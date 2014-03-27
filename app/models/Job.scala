package models

import java.sql.Date
import play.api.libs.json._
import Annotations._

object Job extends ModelCompanion[Job] {
  implicit val format: Format[Job] = Json.format[Job]
  val name = "Job"
}

case class Job (
  id: Option[Long] = None,
  @ApiField(required = true)
  userId: Long,
  @ApiField(required = true)
  company: String,
  @ApiField(required = true)
  start: Date,
  end: Option[Date] = None
) extends Model