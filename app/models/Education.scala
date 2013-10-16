package models

import java.util.Date
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Education {

  def education = {
    get[Pk[Long]]("id") ~
    get[Long]("user_id") ~
    get[String]("school") ~
    get[Option[String]]("degree") ~
    get[Option[String]]("major") ~
    get[Date]("start_date") ~
    get[Option[Date]]("graduated_date")  map {
      case id ~ userId ~ school ~ degree ~ major ~ startDate ~ graduatedDate => 
        Education (id, userId, school, degree, major, startDate, graduatedDate)
    }
  }

  def findByUserId(userId: Long): List[Education] = DB.withConnection { implicit conn =>
    SQL("select * from education where user_id = {userId}")
      .on("userId" -> userId)
      .as(education *)
  }
}

case class Education (
  id: Pk[Long] = NotAssigned,
  userId: Long,
  school: String,
  degree: Option[String],
  major: Option[String],
  startDate: Date,
  graduatedDate: Option[Date]
)