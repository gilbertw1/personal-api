package sql

import models.Bio

import java.util.UUID
import slick.driver.H2Driver.api._

object Bios extends SqlCompanion[Bio,Bios](TableQuery[Bios]) {
  val BIO = tquery
}

class Bios(tag: Tag) extends ApiTable[Bio](tag, "bio") {
  def userId = column[UUID]("user_id")
  def firstname = column[String]("firstname")
  def lastname = column[String]("lastname")
  def middlename = column[Option[String]]("middlename")
  def suffix = column[Option[String]]("suffix")
  def title = column[Option[String]]("title")
  def profile = column[Option[String]]("profile")
  def email = column[Option[String]]("email")
  def phone = column[Option[String]]("phone")
  def githubUsername = column[Option[String]]("github_username")
  def twitterUsername = column[Option[String]]("twitter_username")
  def linkedinUsername = column[Option[String]]("linkedin_username")
  def * = (id, userId, firstname, lastname, middlename,
           suffix, title, profile, email, phone,
           githubUsername, twitterUsername, linkedinUsername) <> ((Bio.apply _).tupled, Bio.unapply _)
}
