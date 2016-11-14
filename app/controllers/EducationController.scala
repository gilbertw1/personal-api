package controllers

import global.Globals
import models.Education
import services.ApiServices

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/education", description = "Education History Information")
class EducationController @Inject() (apiServices: ApiServices) extends ApiModelController(Education, apiServices.educationService) {

  @ApiOperation(value = "Retrieves Education History", response = classOf[Education], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.educationService.findByUserSlug(slug).map(es => Ok(Json.toJson(es)))
  }
}
