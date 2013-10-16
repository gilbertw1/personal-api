package models

import java.util.Date
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Job {

  def job = {
    get[Pk[Long]]("id") ~
    get[Long]("user_id") ~
    get[String]("company") ~
    get[Date]("start") ~
    get[Option[Date]]("end")  map {
      case id ~ userId ~ company ~ start ~ end => 
        Job (id, userId, company, start, end)
    }
  }

  def findByUserId(userId: Long): List[Job] = DB.withConnection { implicit conn =>
    SQL("select * from job where user_id = {userId}")
      .on("userId" -> userId)
      .as(job *)
  }
}

case class Job (
  id: Pk[Long] = NotAssigned,
  userId: Long,
  company: String,
  start: Date,
  end: Option[Date],
  positions: List[Position] = List[Position]()
)