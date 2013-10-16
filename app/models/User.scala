package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object User {

  def user = {
    get[Pk[Long]]("id") ~
    get[String]("slug")  map {
      case id ~ slug => User (id, slug)
    }
  }

  def findBySlug(slug: String): Option[User] = DB.withConnection { implicit conn =>
    SQL("select * from user where slug = {slug}")
      .on("slug" -> slug)
      .as(user *)
      .headOption
  }

  def findById(id: Long): Option[User] = DB.withConnection { implicit conn =>
    SQL("select * from user where id = {id}")
      .on("id" -> id)
      .as(user *)
      .headOption
  }

}

case class User (
  id: Pk[Long] = NotAssigned,
  slug: String
)