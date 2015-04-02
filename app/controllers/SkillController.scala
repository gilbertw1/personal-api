package controllers

import global.Globals
import models.Skill

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._

@Api(value = "/skills", description = "Skill Information")
object SkillController extends PersonalApiModelController(Skill) {

  @ApiOperation(value = "Retrieves List of Skills", response = classOf[Skill], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)
      val skills = Skills.findByUserSlug(slug)
      Ok(toJson(skills))
    }
  }

  def filtered() = CORSAction { implicit request =>
    withDBSession { implicit session =>
      val criterias = createFilterCriteria(request)
      Ok(toJson(Skills.runQuery(criterias)))
    }
  }
}