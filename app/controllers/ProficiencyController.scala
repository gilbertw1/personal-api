package controllers

import global.Globals
import models.Proficiency
import services.ApiServices

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/proficiencies", description = "Proficiency Information")
class ProficiencyController @Inject() (apiServices: ApiServices) extends ApiModelController(Proficiency, apiServices.proficiencyService) {

  @ApiOperation(value = "Retrieves List of Proficiencies", response = classOf[Proficiency], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.proficiencyService.findByUserSlug(slug).map(ps => Ok(Json.toJson(ps)))
  }
}
