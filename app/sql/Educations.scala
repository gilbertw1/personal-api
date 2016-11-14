package sql

import models.Education

import java.util.UUID
import org.joda.time.DateTime
import slick.driver.H2Driver.api._
import com.github.tototoshi.slick.H2JodaSupport._

object Educations extends SqlCompanion[Education,Educations](TableQuery[Educations]) {
  val EDUCATION = tquery
}

class Educations(tag: Tag) extends ApiTable[Education](tag, "education") {
  def userId = column[UUID]("user_id")
  def school = column[String]("school")
  def degree = column[Option[String]]("degree")
  def major = column[Option[String]]("major")
  def startDate = column[DateTime]("start_date")
  def graduatedDate = column[Option[DateTime]]("graduated_date")
  def * = (id, userId, school, degree, major, startDate, graduatedDate) <>
    ((Education.apply _).tupled, Education.unapply _)
}
