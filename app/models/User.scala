package models

import java.util.UUID
import play.api.libs.json._
import Annotations._

object User extends ModelCompanion[User] {
  implicit val format: Format[User] = Json.format[User]
  val name = "User"
}

case class User (
  id: UUID,
  @ApiField(required = true)
  slug: String
) extends Model[User] {
  val companion = User
}
