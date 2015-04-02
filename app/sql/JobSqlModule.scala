package sql

import models._
import java.sql.Date

trait JobSqlModule { this: SqlModule with UserSqlModule =>
  import profile.simple._

  object Jobs extends SqlCompanion[Job,Jobs](Job,TableQuery[Jobs]) {
    val JOBS = db

    def findByUserSlug(userSlug: String)(implicit session: Session): Seq[Job] = {
      val jobQuery = for (
        u <- Users.db if u.slug === userSlug;
        j <- Jobs.db if j.userId === u.id
      ) yield (j)
      jobQuery.list
    }
  }

  class Jobs(tag: Tag) extends PersonalApiTable[Job](tag, "job") {
    def userId = column[Long]("user_id")
    def company = column[String]("company")
    def start = column[Date]("start")
    def end = column[Date]("end", O.Nullable)
    def * = (id.?, userId, company, start, end.?) <> ((Job.apply _).tupled, Job.unapply _)

    def user = foreignKey("job_user_fk", userId, Users.db)(_.id)

    override def filterableColumnByName = Map[String,FilterableColumn[_]] (
      "userId" -> FilterableColumn(userId, LongField),
      "company" -> FilterableColumn(company, StringField)
    )
  }
}