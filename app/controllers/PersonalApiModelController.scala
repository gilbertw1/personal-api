package controllers

import models._
import global.Globals

import scala.util.{Try,Success,Failure}

import play.api._
import play.api.mvc._
import play.api.libs.json._

import Globals.databaseModule._
import profile.simple._

abstract class PersonalApiModelController[T <: Model](companion: ModelCompanion[T]) extends PersonalApiController {

  implicit val format: Format[T] = companion.format
  val sql = sqlCompanionFor(companion)

  def list = CORSAction { implicit request =>
    withDBSession { implicit session =>
      Ok(Json.toJson(sql.list))
    }
  }

  def create = CORSAction { implicit request =>
    withJson[T] { model =>
      withDBSession { implicit session =>
        val id = sql.insert(model)
        Ok(s"""{"created": ${id}}""")
      }
    }
  }

  def show(id: Long) = CORSAction { implicit request =>
    withDBSession { implicit session =>
      val maybeModel = sql.findById(id)
      maybeModel match {
        case Some(m) => Ok(Json.toJson(m))
        case None => NotFound
      }
    }
  }

  def delete(id: Long) = CORSAction { implicit request =>
    withDBSession { implicit session =>
      val deleted = sql.delete(id)
      Ok(s"""{"deleted": "${deleted}"}""")
    }
  }

  def update(id: Long) = CORSAction { implicit request =>
    withJson[JsObject] { updateObj =>
      withDBSession { implicit session =>
        val mOpt = sql.findById(id)
        mOpt match {
          case Some(m) =>
            val patched = companion.patch(m, updateObj)
            sql.update(id, patched)
            Ok(Json.toJson(patched))
          case None =>
            NotFound
        }
      }
    }
  }

  def replace(id: Long) = CORSAction { implicit request =>
    withJson[T] { model =>
      withDBSession { implicit session =>
        Try {
          sql.update(id, model)
        } match {
          case Success(_) => Ok
          case Failure(e) => NotFound
        }
      }
    }
  }
}