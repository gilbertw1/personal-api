package services

import models.Model
import sql.{ ApiTable, SqlCompanion }

import java.util.UUID
import scala.concurrent.{ Future, ExecutionContext }

import play.api.libs.json.{ Json, Format }
import slick.driver.H2Driver.api._

abstract class DatabaseService[T <: Model[_], U <: ApiTable[T]]
  (companion: SqlCompanion[T, U], database: Database) {

  implicit val db = database

  val tquery = companion.tquery

  def list(offset: Int = 0, limit: Int = 50): Future[Seq[T]] = {
    val query = for (m <- tquery) yield (m)
    db.run(query.drop(offset).take(limit).result)
  }

  def insert(model: T)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run(tquery += model).map(_ > 0)
  }

  def insertAll(models: Seq[T]): Future[Option[Int]] = {
    db.run(tquery ++= models)
  }

  def findById(id: UUID): Future[Option[T]] = {
    db.run(tquery.filter(_.id === id).result.headOption)
  }

  def delete(id: UUID)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run(tquery.filter(_.id === id).delete).map(_ > 0)
  }

  def update(id: UUID, model: T)(implicit ec: ExecutionContext): Future[Boolean] = {
    val query = for { ms <- tquery if ms.id === id } yield (ms)
    db.run(query.update(model)).map(_ > 0)
  }
}
