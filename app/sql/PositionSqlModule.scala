package sql

import models._
import java.sql.Date

trait PositionSqlModule { this: SqlModule with JobSqlModule =>
  import profile.simple._

  object Positions extends SqlCompanion[Position,Positions](Position,TableQuery[Positions]) {
    val POSITIONS = db
  }

  class Positions(tag: Tag) extends PersonalApiTable[Position](tag, "position") {
    def jobId = column[Long]("job_id")
    def title = column[String]("title")
    def description = column[String]("description", O.Nullable)
    def * = (id.?, jobId, title, description.?) <> ((Position.apply _).tupled, Position.unapply _)

    def job = foreignKey("position_job_fk", jobId, Jobs.db)(_.id)

    override def filterableColumnByName = Map[String,FilterableColumn[_]] (
      "jobId" -> FilterableColumn(jobId, LongField),
      "title" -> FilterableColumn(title, StringField),
      "description" -> FilterableColumn(description, StringField)
    )
  }
}