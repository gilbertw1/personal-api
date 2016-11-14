package models

import java.util.UUID
import org.joda.time.DateTime
import play.api.libs.json._
import Annotations._

object Education extends ModelCompanion[Education] {
  implicit val format: Format[Education] = Json.format[Education]
  val name = "Education"
}

case class Education (
  val id: UUID,
  @ApiField(required = true)
  val userId: UUID,
  @ApiField(required = true)
  val school: String,
  val degree: Option[String] = None,
  val major: Option[String] = None,
  @ApiField(required = true)
  val startDate: DateTime,
  val graduatedDate: Option[DateTime] = None
) extends Model[Education] {
  val companion = Education
}
