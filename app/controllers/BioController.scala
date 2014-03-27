package controllers

import models.Bio
import global.Globals

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._

@Api(value = "/bio", description = "Biographical Information")
object BioController extends PersonalApiModelController(Bio) {

  @ApiOperation(value = "Retrieves Basic Bio Info", response = classOf[Bio], httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)
      val bioOpt = Bios.findOneByUserSlug(slug)
      bioOpt.map(b => Ok(toJson(b))).getOrElse(NotFound)
    }
  }
}