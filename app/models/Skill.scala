package models

import java.util.UUID
import play.api.libs.json._
import Annotations._

object Skill extends ModelCompanion[Skill] {
  implicit val format: Format[Skill] = Json.format[Skill]
  val name = "Skill"
}

case class Skill (
  id: UUID,
  @ApiField(required = true)
  userId: UUID,
  @ApiField(required = true)
  title: String,
  @ApiField(required = true)
  description: String
) extends Model[Skill] {
  val companion = Skill
}
