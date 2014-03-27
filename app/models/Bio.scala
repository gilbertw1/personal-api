package models

import play.api.libs.json._
import Annotations._

object Bio extends ModelCompanion[Bio] {
  implicit val format: Format[Bio] = Json.format[Bio]
  val name = "Bio"
}

case class Bio (
  id: Option[Long] = None,
  @ApiField(required = true)
  userId: Long,
  @ApiField(required = true)
  firstname: String,
  @ApiField(required = true)
  lastname: String,
  middlename: Option[String] = None,
  suffix: Option[String] = None,
  title: Option[String] = None,
  profile: Option[String] = None,
  email: Option[String] = None,
  phone: Option[String] = None,
  githubUsername: Option[String] = None,
  twitterUsername: Option[String] = None,
  linkedinUsername: Option[String] = None
) extends Model