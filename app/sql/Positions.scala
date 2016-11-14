package sql

import models.Position

import java.util.UUID
import org.joda.time.DateTime
import slick.driver.H2Driver.api._
import com.github.tototoshi.slick.H2JodaSupport._

object Positions extends SqlCompanion[Position,Positions](TableQuery[Positions]) {
  val POSITION = tquery
}

class Positions(tag: Tag) extends ApiTable[Position](tag, "position") {
  def jobId = column[UUID]("job_id")
  def title = column[String]("title")
  def description = column[Option[String]]("description")
  def * = (id, jobId, title, description) <> ((Position.apply _).tupled, Position.unapply _)
}
