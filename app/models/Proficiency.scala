package models

import java.util.UUID
import play.api.libs.json._
import Annotations._

object Proficiency extends ModelCompanion[Proficiency] {
  implicit val format: Format[Proficiency] = Json.format[Proficiency]
  val name = "Proficiency"
}

case class Proficiency (
  id: UUID,
  @ApiField(required = true)
  userId: UUID,
  @ApiField(required = true)
  title: String
) extends Model[Proficiency] {
  val companion = Proficiency
}
