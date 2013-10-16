package util

import anorm._
import play.api.libs.json._

object JsonHelper {
  implicit object PkFormat extends Format[Pk[Long]] {
      def reads(json: JsValue): JsResult[Pk[Long]] = JsSuccess(json.asOpt[Long].map(id => Id(id)).getOrElse(NotAssigned))
      def writes(id: Pk[Long]): JsValue = id.map(JsNumber(_)).getOrElse(JsNull)
  }
}