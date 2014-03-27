package models

import play.api.libs.json._

abstract class ModelCompanion[T <: Model] {
  implicit val format: Format[T]
  def name: String

  def fromJson(json: String): T = Json.parse(json).as[T]
  def toJson(model: T): String = Json.stringify(Json.toJson(model))
  def fromJsonArray(json: String) = Json.parse(json).as[Seq[T]]
  def toJsonArray(model: Seq[T]): String = Json.stringify(Json.toJson(model))
  def patch(model: T, json: String): T = patch(model, Json.parse(json).as[JsObject])
  def patch(model: T, jsObj: JsObject): T = (Json.toJson(model).as[JsObject] ++ jsObj).as[T]
  def toJsObject(model: T): JsObject = Json.toJson(model).as[JsObject]
  def fromJsObject(obj: JsObject) = obj.as[JsObject]
}