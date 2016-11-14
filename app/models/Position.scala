package models

import java.util.UUID
import play.api.libs.json._
import Annotations._

object Position extends ModelCompanion[Position] {
  implicit val format: Format[Position] = Json.format[Position]
  val name = "Position"
}

case class Position (
  id: UUID,
  @ApiField(required = true)
  jobId: UUID,
  @ApiField(required = true)
  title: String,
  description: Option[String] = None
) extends Model[Position] {
  val companion = Position
}
