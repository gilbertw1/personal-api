package sql

import models._
import java.sql.Date

trait EducationSqlModule { this: SqlModule with UserSqlModule =>
  import profile.simple._

  object Educations extends SqlCompanion[Education,Educations](Education,TableQuery[Educations]) {
    val EDUCATIONS = db

    def findByUserSlug(userSlug: String)(implicit session: Session): Seq[Education] = {
      val educationQuery = for (
        u <- Users.db if u.slug === userSlug;
        e <- Educations.db if e.userId === u.id
      ) yield (e)
      educationQuery.list
    }
  }

  class Educations(tag: Tag) extends PersonalApiTable[Education](tag, "education") {
    def userId = column[Long]("user_id")
    def school = column[String]("school")
    def degree = column[String]("degree", O.Nullable)
    def major = column[String]("major", O.Nullable)
    def startDate = column[Date]("start_date")
    def graduatedDate = column[Date]("graduated_date", O.Nullable)
    def * = (id.?, userId, school, degree.?, major.?, startDate, graduatedDate.?) <> ((Education.apply _).tupled, Education.unapply _)

    def user = foreignKey("education_user_fk", userId, Users.db)(_.id)
  }
}