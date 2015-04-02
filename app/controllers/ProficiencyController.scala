package controllers

import global.Globals
import models.Proficiency

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._

@Api(value = "/proficiencies", description = "Proficiency Information")
object ProficiencyController extends PersonalApiModelController(Proficiency) {

  @ApiOperation(value = "Retrieves List of Proficiencies", response = classOf[Proficiency], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)
      val proficiencies = Proficiencies.findByUserSlug(slug)
      Ok(toJson(proficiencies))
    }
  }

  def filtered() = CORSAction { implicit request =>
    withDBSession { implicit session =>
      val criterias = createFilterCriteria(request)
      Ok(toJson(Proficiencies.runQuery(criterias)))
    }
  }
}