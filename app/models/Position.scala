package models

import play.api.libs.json._
import Annotations._

object Position extends ModelCompanion[Position] {
  implicit val format: Format[Position] = Json.format[Position]
  val name = "Position"
}

case class Position (
  id: Option[Long] = None,
  @ApiField(required = true)
  jobId: Long,
  @ApiField(required = true)
  title: String,
  description: Option[String] = None
) extends Model