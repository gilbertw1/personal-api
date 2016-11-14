package controllers

import global.Globals
import models.Profile
import services.ApiServices

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/profile", description = "Full Profile")
class ProfileController @Inject() (apiServices: ApiServices) extends ApiController {

  implicit val profileFmt = Profile.format

  @ApiOperation(value = "Returns entire profile", response = classOf[Profile], httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.userService.findProfileByUserSlug(slug).map {
      case Some(profile) => Ok(Json.toJson(profile))
      case None => NotFound
    }
  }
}

