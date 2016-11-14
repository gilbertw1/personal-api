package controllers

import models._
import global.Globals

import scala.concurrent.Future
import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json.{ Json, JsError, Format }
import play.api.libs.concurrent.Execution.Implicits.defaultContext

abstract class ApiController extends Controller {

  def withJson[T](f: (T) => Future[Result])
                 (implicit request: Request[AnyContent], format: Format[T]): Future[Result] = {
    request.body.asJson map { json =>
      json.validate[T] map { parsedValue: T =>
        f(parsedValue)
      } recoverTotal { e =>
        Future.successful(BadRequest("Error Parsing Json: " + JsError.toFlatJson(e)))
      }
    } getOrElse {
      Future.successful(BadRequest("Bad Content-Type"))
    }
  }
}
