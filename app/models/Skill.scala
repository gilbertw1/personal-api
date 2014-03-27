package models

import play.api.libs.json._
import Annotations._

object Skill extends ModelCompanion[Skill] {
  implicit val format: Format[Skill] = Json.format[Skill]
  val name = "Skill"
}

case class Skill (
  id: Option[Long] = None,
  @ApiField(required = true)
  userId: Long,
  @ApiField(required = true)
  title: String,
  @ApiField(required = true)
  description: String
) extends Model