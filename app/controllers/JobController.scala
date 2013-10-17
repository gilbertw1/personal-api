package controllers

import models.{Job,Position}
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/jobs", listingPath = "/api-docs/jobs", description = "Job History Information")
object JobController extends Controller {

  implicit val positionFormat = format[Position]
  implicit val jobFormat = format[Job]

  @ApiOperation(value = "Retrieves Job History", responseClass = "models.Job", httpMethod = "GET")
  def get = CORSAction {
    val jobs = Job.findByUserId(Global.userId)
    val jobsWithPositions = jobs.map(j => j.copy(positions = Position.findByJobId(j.id.get)))

    Ok(stringify(toJson(jobsWithPositions)))
  }
  
}