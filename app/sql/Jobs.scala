package sql

import models.Job

import java.util.UUID
import org.joda.time.DateTime
import slick.driver.H2Driver.api._
import com.github.tototoshi.slick.H2JodaSupport._

object Jobs extends SqlCompanion[Job,Jobs](TableQuery[Jobs]) {
  val JOB = tquery
}

class Jobs(tag: Tag) extends ApiTable[Job](tag, "job") {
  def userId = column[UUID]("user_id")
  def company = column[String]("company")
  def start = column[DateTime]("start")
  def end = column[Option[DateTime]]("end")
  def * = (id, userId, company, start, end) <> ((Job.apply _).tupled, Job.unapply _)
}
