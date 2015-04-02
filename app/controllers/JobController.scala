package controllers

import global.Globals
import models.{Job,Position,JobWithPositions}

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._
import Jobs._
import Positions._
import Users._

@Api(value = "/jobs", description = "Job History Information")
object JobController extends PersonalApiModelController(Job) {

  implicit val positionFormat = Position.format
  implicit val jobWithPositionFormat = Json.format[JobWithPositions]

  @ApiOperation(value = "Retrieves Job History", response = classOf[JobWithPositions], responseContainer = "List", httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)
      val jobPositionsQuery = for (
        u <- USERS if u.slug === slug;
        j <- JOBS if j.userId === u.id;
        p <- POSITIONS if p.jobId === j.id
      ) yield (j,p)

      val jobsWithPositions = JobWithPositions.extract(jobPositionsQuery.list)
      Ok(toJson(jobsWithPositions))
    }
  }

  def filtered() = CORSAction { implicit request =>
    withDBSession { implicit session =>
      val criterias = createFilterCriteria(request)
      Ok(toJson(Jobs.runQuery(criterias)))
    }
  }
}