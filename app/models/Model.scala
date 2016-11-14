package models

import java.util.UUID
import play.api.libs.json._

trait Model[Self <: AnyRef] { this: Self =>
  def id: UUID
  def companion: ModelCompanion[Self]

  def toJson(): String = companion.toJson(this)
  def patch(json: String): Self = companion.patch(this, json)
  def patch(jsObj: JsObject): Self = companion.patch(this, jsObj.asInstanceOf[JsObject])
  def toJsObject(): JsObject = companion.toJsObject(this)
}

abstract class ModelCompanion[T <: AnyRef] {
  implicit val format: Format[T]
  val name: String

  def fromJson(json: String): T = Json.parse(json).as[T]
  def toJson(model: T): String = Json.stringify(Json.toJson(model))
  def fromJsonArray(json: String) = Json.parse(json).as[Seq[T]]
  def toJsonArray(model: Seq[T]): String = Json.stringify(Json.toJson(model))
  def patch(model: T, json: String): T = patch(model, Json.parse(json).as[JsObject])
  def patch(model: T, jsObj: JsObject): T = (Json.toJson(model).as[JsObject] ++ jsObj).as[T]
  def toJsObject(model: T): JsObject = Json.toJson(model).as[JsObject]
  def fromJsObject(obj: JsObject): T = obj.as[T]
}
