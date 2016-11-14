package models

import java.util.UUID
import org.joda.time.DateTime
import play.api.libs.json._
import Annotations._

object Job extends ModelCompanion[Job] {
  implicit val format: Format[Job] = Json.format[Job]
  val name = "Job"
}

case class Job (
  id: UUID,
  @ApiField(required = true)
  userId: UUID,
  @ApiField(required = true)
  company: String,
  @ApiField(required = true)
  start: DateTime,
  end: Option[DateTime] = None
) extends Model[Job] {
  val companion = Job
}
