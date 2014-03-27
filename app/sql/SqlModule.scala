package sql

import models._
import scala.slick.driver.{H2Driver,MySQLDriver,JdbcProfile}

trait SqlModule {
  val profile: JdbcProfile

  import profile.simple._

  abstract class PersonalApiTable[T <: Model](tag: Tag, tname: String) extends Table[T](tag, tname) {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
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
      db.where(_.id === id).firstOption
    }

    def delete(id: Long)(implicit session: Session): Boolean = {
      val rowsDeleted = db.where(_.id === id).delete
      rowsDeleted > 0
    }

    def update(id: Long, model: T)(implicit session: Session) {
      val query = for { ms <- db if ms.id === id } yield (ms)
      query.update(model)
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