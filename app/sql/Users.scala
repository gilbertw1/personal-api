package sql

import models.User
import slick.driver.H2Driver.api._

object Users extends SqlCompanion[User,Users](TableQuery[Users]) {
  val USER = tquery
}

class Users(tag: Tag) extends ApiTable[User](tag, "user") {
  def slug = column[String]("slug")
  def * = (id, slug) <> ((User.apply _).tupled, User.unapply _)
}
