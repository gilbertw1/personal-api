package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Skill {

  def skill = {
    get[Pk[Long]]("id") ~
    get[Long]("user_id") ~
    get[String]("title") ~
    get[String]("description") map {
      case id ~ userId ~ title ~ description => 
        Skill (id, userId, title, description)
    }
  }

  def findByUserId(userId: Long): List[Skill] = DB.withConnection { implicit conn =>
    SQL("select * from skill where user_id = {userId}")
      .on("userId" -> userId)
      .as(skill *)
  }
}

case class Skill (
  id: Pk[Long] = NotAssigned,
  userId: Long,
  title: String,
  description: String
)