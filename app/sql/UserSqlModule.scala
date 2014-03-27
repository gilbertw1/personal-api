package sql

import models._

trait UserSqlModule { this: SqlModule =>
  import profile.simple._

  object Users extends SqlCompanion[User,Users](User,TableQuery[Users]) {
    val USERS = db
  }

  class Users(tag: Tag) extends PersonalApiTable[User](tag, "user") {
    def slug = column[String]("slug")
    def * = (id.?, slug) <> ((User.apply _).tupled, User.unapply _)
  }
}