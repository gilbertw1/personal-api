package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Bio {

  def bio = {
    get[Pk[Long]]("id") ~
    get[Long]("user_id") ~
    get[Option[String]]("firstname") ~
    get[Option[String]]("lastname") ~
    get[Option[String]]("middlename") ~
    get[Option[String]]("suffix") ~
    get[Option[String]]("title") ~
    get[Option[String]]("profile") ~
    get[Option[String]]("email") ~
    get[Option[String]]("phone") ~
    get[Option[String]]("github_username") ~
    get[Option[String]]("twitter_username") ~
    get[Option[String]]("linkedin_username") map {
      case id ~ userId ~ firstname ~ lastname ~ middlename ~ suffix ~ title ~ profile ~ 
            email ~ phone ~ githubUsername ~ twitterUsername ~ linkedinUsername => 
        Bio (id, userId, firstname, lastname, middlename, suffix, title, profile, email, phone, githubUsername, twitterUsername, linkedinUsername)
    }
  }

  def findOneByUserId(userId: Long): Option[Bio] = DB.withConnection { implicit conn =>
    SQL("select * from bio where user_id = {userId}")
      .on("userId" -> userId)
      .as(bio *)
      .headOption
  }
}

case class Bio (
  id: Pk[Long] = NotAssigned,
  userId: Long,
  firstname: Option[String],
  lastname: Option[String],
  middlename: Option[String],
  suffix: Option[String],
  title: Option[String],
  profile: Option[String],
  email: Option[String],
  phone: Option[String],
  githubUsername: Option[String],
  twitterUsername: Option[String],
  linkedinUsername: Option[String]
)