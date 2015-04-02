package sql

import models._
import scala.util.Try
import scala.slick.driver.{H2Driver,MySQLDriver,JdbcProfile}
import scala.slick.ast.{TypedType, ColumnOption}

trait SqlModule {
  val profile: JdbcProfile

  import profile.simple._

  abstract class PersonalApiTable[T <: Model](tag: Tag, tname: String) extends Table[T](tag, tname) {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def filterableColumnByName: Map[String,FilterableColumn[_]] = Map[String,FilterableColumn[_]]()
    def getFilterableColumnByName[T](name: String): Option[FilterableColumn[T]] = filterableColumnByName.get(name).map(_.asInstanceOf[FilterableColumn[T]])
    def orderColumn(direction: String, column: Column[_]) = if (direction.toLowerCase == "asc") column.asc else column.desc
  }

  abstract class SqlCompanion[T <: Model, U <: PersonalApiTable[T]](val companion: ModelCompanion[T], val db: TableQuery[U]) {
    val dbWithId = db returning db.map(_.id)

    def self(): SqlCompanion[T,U] = this

    def list(implicit session: Session): Seq[T] = {
      val query = for (m <- db) yield (m)
      query.list
    }

    def insert(model: T)(implicit session: Session): Long = {
      (dbWithId += model)
    }

    def findById(id: Long)(implicit session: Session): Option[T] = {
      db.filter(_.id === id).firstOption
    }

    def delete(id: Long)(implicit session: Session): Boolean = {
      val rowsDeleted = db.filter(_.id === id).delete
      rowsDeleted > 0
    }

    def update(id: Long, model: T)(implicit session: Session) {
      val query = for { ms <- db if ms.id === id } yield (ms)
      query.update(model)
    }

    def runQuery(criterias: Seq[FilterCriteria])(implicit session: Session): Seq[T] = {
      var query = for (row <- db) yield row
      criterias.foreach { criteria =>
        query = Try(query.filter { table =>
          val fcol = table.getFilterableColumnByName(criteria.fieldName).get
          applyFilterableColumn(fcol, criteria.value)
        }).getOrElse(query)
      }
      query.list
    }
  }

  def applyFilterableColumn(fcol: FilterableColumn[_], value: String): Column[Boolean] = {
    (if(fcol.filterType == IntegerField) {
      fcol.column.asInstanceOf[Column[Int]] === IntegerField.extract(value)
    } else if(fcol.filterType == StringField) {
      fcol.column.asInstanceOf[Column[String]] === StringField.extract(value)
    } else if(fcol.filterType == LongField) {
      fcol.column.asInstanceOf[Column[Long]] === LongField.extract(value)
    }).asInstanceOf[Column[Boolean]]
  }

  case class FilterCriteria(fieldName: String, value: String)
  case class FilterableColumn[T](column: Column[T], filterType: FilterableType[T])

  sealed trait FilterableType[T] { def extract(value: String): T }

  case object IntegerField extends FilterableType[Int] {
    def extract(value: String): Int = {
      value.toInt
    }
  }
  case object StringField extends FilterableType[String] {
    def extract(value: String): String = {
      value
    }
  }

  case object LongField extends FilterableType[Long] {
    def extract(value: String): Long = {
      value.toLong
    }
  }
}

trait PersonalApiSqlModule
  extends BioSqlModule
  with EducationSqlModule
  with JobSqlModule
  with PositionSqlModule
  with ProficiencySqlModule
  with SkillSqlModule
  with UserSqlModule { this: SqlModule =>

    def sqlCompanionFor[T <: Model, U <: PersonalApiTable[T]](companion: ModelCompanion[T]): SqlCompanion[T,U] = {
      val sqlCompanion = companion match {
        case Bio => Bios
        case Education => Educations
        case Job => Jobs
        case Position => Positions
        case Proficiency => Proficiencies
        case Skill => Skills
        case User => Users
        case _ => throw new Exception("No sql model companion mapping defined for: " + companion)
      }
      sqlCompanion.asInstanceOf[SqlCompanion[T,U]]
    }
}

object H2SqlModule extends SqlModule with PersonalApiSqlModule {
  val profile: JdbcProfile = H2Driver
}

object MysqlModule extends SqlModule with PersonalApiSqlModule {
  val profile: JdbcProfile = MySQLDriver
}