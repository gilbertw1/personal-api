package controllers

import global.Globals
import models.Skill
import services.ApiServices

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/skills", description = "Skill Information")
class SkillController @Inject() (apiServices: ApiServices) extends ApiModelController(Skill, apiServices.skillService) {

  @ApiOperation(value = "Retrieves List of Skills", response = classOf[Skill], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.skillService.findByUserSlug(slug).map(s => Ok(Json.toJson(s)))
  }
}
