package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Proficiency {

  def proficiency = {
    get[Pk[Long]]("id") ~
    get[Long]("user_id") ~
    get[String]("title") map {
      case id ~ userId ~ title => 
        Proficiency (id, userId, title)
    }
  }

  def findByUserId(userId: Long): List[Proficiency] = DB.withConnection { implicit conn =>
    SQL("select * from proficiency where user_id = {userId}")
      .on("userId" -> userId)
      .as(proficiency *)
  }
}

case class Proficiency (
  id: Pk[Long] = NotAssigned,
  userId: Long,
  title: String
)