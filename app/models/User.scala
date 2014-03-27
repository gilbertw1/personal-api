package models

import play.api.libs.json._
import Annotations._

object User extends ModelCompanion[User] {
  implicit val format: Format[User] = Json.format[User]
  val name = "User"
}

case class User (
  id: Option[Long] = None,
  @ApiField(required = true)
  slug: String
) extends Model