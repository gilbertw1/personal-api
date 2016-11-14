package controllers

import global.Globals
import models.{ Job, JobWithPositions }
import services.ApiServices

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import io.swagger.core._
import io.swagger.annotations._

import play.api.libs.concurrent.Execution.Implicits._

@Api(value = "/jobs", description = "Job History Information")
class JobController @Inject() (apiServices: ApiServices) extends ApiModelController(Job, apiServices.jobService) {

  implicit val jobWithPositionFormat = JobWithPositions.format

  @ApiOperation(value = "Retrieves Job History", response = classOf[JobWithPositions], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    val slug = userSlug.getOrElse(Globals.defaultUserSlug)
    apiServices.jobService.findJobsWithPositionsBySlug(slug).map(jwps => Ok(Json.toJson(jwps)))
  }
}
