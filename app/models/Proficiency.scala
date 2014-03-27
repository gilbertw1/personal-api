package models

import play.api.libs.json._
import Annotations._

object Proficiency extends ModelCompanion[Proficiency] {
  implicit val format: Format[Proficiency] = Json.format[Proficiency]
  val name = "Proficiency"
}

case class Proficiency (
  id: Option[Long] = None,
  @ApiField(required = true)
  userId: Long,
  @ApiField(required = true)
  title: String
) extends Model