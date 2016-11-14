package controllers

import models.{ Model, ModelCompanion}
import services.DatabaseService
import sql.ApiTable

import java.util.UUID
import scala.concurrent.Future
import scala.async.Async.{ async, await }
import scala.util.{ Try, Success, Failure }

import play.api._
import play.api.mvc._
import play.api.libs.json.{ Json, Format, JsObject }
import play.api.libs.concurrent.Execution.Implicits._

abstract class ApiModelController[T <: Model[_], U <: ApiTable[T]](companion: ModelCompanion[T],
                                                                service: DatabaseService[T,U]) extends ApiController {

  implicit val format: Format[T] = companion.format

  def list(offset: Int, limit: Int) = CORSAction { implicit request =>
    service.list(offset, limit).map(ms => Ok(Json.toJson(ms)))
  }

  def create = CORSAction { implicit request =>
    withJson[T] { model =>
      service.insert(model).map(_ => Ok(s"""{"created": ${model.id}}"""))
    }
  }

  def show(id: UUID) = CORSAction { implicit request =>
    service.findById(id).map {
      case Some(model) => Ok(Json.toJson(model))
      case None => NotFound
    }
  }

  def delete(id: UUID) = CORSAction { implicit request =>
    service.delete(id).map(id => Ok(s"""{"deleted": "${id}"}"""))
  }

  def update(id: UUID) = CORSAction { implicit request =>
    withJson[JsObject] { updateObj =>
      async {
        val mOpt = await(service.findById(id))
        if (mOpt.isDefined) {
          val patched = companion.patch(mOpt.get, updateObj)
          val success = await(service.update(id, patched))
          Ok(Json.toJson(patched))
        } else {
          NotFound
        }
      }
    }
  }

  def replace(id: UUID) = CORSAction { implicit request =>
    withJson[T] { model =>
      service.update(id, model).map(_ => Ok)
    }
  }
}
