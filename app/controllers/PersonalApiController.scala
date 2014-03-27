package controllers

import models._
import global.Globals

import scala.concurrent._
import scala.slick.driver.MySQLDriver.simple.{Session => DBSession}

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

trait PersonalApiController extends Controller {

  def withDBSession[T](f: (DBSession) => T): Future[T] = {
    Future {
      Globals.db.withSession { implicit session =>
        f(session)
      }
    }(Globals.sqlContext)
  }

  def withJson[T](f: (T) => Future[SimpleResult])(implicit request: Request[AnyContent], format: Format[T]): Future[SimpleResult] = {
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

  def toJson[T](obj: T)(implicit format: Format[T]): String = {
    Json.stringify(Json.toJson(obj))
  }
}