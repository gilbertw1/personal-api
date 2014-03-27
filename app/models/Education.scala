package models

import java.sql.Date
import play.api.libs.json._
import Annotations._

object Education extends ModelCompanion[Education] {
  implicit val format: Format[Education] = Json.format[Education]
  val name = "Education"
}

case class Education (
  val id: Option[Long] = None,
  @ApiField(required = true)
  val userId: Long,
  @ApiField(required = true)
  val school: String,
  val degree: Option[String] = None,
  val major: Option[String] = None,
  @ApiField(required = true)
  val startDate: Date,
  val graduatedDate: Option[Date] = None
) extends Model