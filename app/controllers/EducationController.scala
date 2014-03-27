package controllers

import global.Globals
import models.Education

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._

@Api(value = "/education", description = "Education History Information")
object EducationController extends PersonalApiModelController(Education) {

  @ApiOperation(value = "Retrieves Education History", response = classOf[Education], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)
      val educations = Educations.findByUserSlug(slug)
      Ok(toJson(educations))
    }
  }  
}