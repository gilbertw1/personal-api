package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Position {

  def position = {
    get[Pk[Long]]("id") ~
    get[Long]("job_id") ~
    get[String]("title") ~
    get[Option[String]]("description") map {
      case id ~ jobId ~ title ~ description => 
        Position (id, jobId, title, description)
    }
  }

  def findByJobId(jobId: Long): List[Position] = DB.withConnection { implicit conn =>
    SQL("select * from position where job_id = {jobId}")
      .on("jobId" -> jobId)
      .as(position *)
  }
}

case class Position (
  id: Pk[Long] = NotAssigned,
  jobId: Long,
  title: String,
  description: Option[String]
)