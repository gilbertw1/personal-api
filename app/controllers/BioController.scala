package controllers

import models.Bio
import services.ApiServices
import global.Globals

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/bio", description = "Biographical Information")
class BioController @Inject() (apiServices: ApiServices) extends ApiModelController(Bio, apiServices.bioService) {

  @ApiOperation(value = "Retrieves Basic Bio Info", response = classOf[Bio], httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.bioService.findOneByUserSlug(slug).map {
      case Some(bio) => Ok(Json.toJson(bio))
      case None => NotFound
    }
  }
}
