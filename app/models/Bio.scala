package models

import java.util.UUID
import play.api.libs.json._
import Annotations._

object Bio extends ModelCompanion[Bio] {
  implicit val format: Format[Bio] = Json.format[Bio]
  val name = "Bio"
}

case class Bio (
  id: UUID,
  @ApiField(required = true)
  userId: UUID,
  @ApiField(required = true)
  firstname: String,
  @ApiField(required = true)
  lastname: String,
  middlename: Option[String],
  suffix: Option[String],
  title: Option[String],
  profile: Option[String],
  email: Option[String],
  phone: Option[String],
  githubUsername: Option[String],
  twitterUsername: Option[String],
  linkedinUsername: Option[String]
) extends Model[Bio] {
  val companion = Bio
}
