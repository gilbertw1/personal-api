package controllers

import global.Globals
import models._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import Globals.databaseModule._
import profile.simple._

import Annotations._
import Users._
import Bios._
import Jobs._
import Positions._
import Educations._
import Skills._
import Proficiencies._

@Api(value = "/profile", description = "Full Profile")
object ProfileController extends PersonalApiController {

  implicit val bioFormat = Bio.format
  implicit val educationFormat = Education.format
  implicit val positionFormat = Position.format
  implicit val jobFormat = Job.format
  implicit val proficiencyFormat = Proficiency.format
  implicit val skillFormat = Skill.format
  implicit val jobWithPositions = Json.format[JobWithPositions]
  implicit val profileFormat = Json.format[Profile]  

  @ApiOperation(value = "Returns entire profile", response = classOf[Profile], httpMethod = "GET")
  def get(userSlug: Option[String]) = CORSAction {
    withDBSession { implicit session =>
      val slug = userSlug.getOrElse(Globals.defaultUserSlug)

      val profileQuery = for (
        user <- USERS if user.slug === slug;
        bio <- BIOS if bio.userId === user.id;
        edu <- EDUCATIONS if edu.userId === user.id;
        job <- JOBS if job.userId === user.id;
        pos <- POSITIONS if pos.jobId === job.id;
        skill <- SKILLS if skill.userId === user.id;
        prof <- PROFICIENCIES if prof.userId === user.id
      ) yield (user,bio,edu,job,pos,skill,prof)

      val results = profileQuery.list

      if(!results.isEmpty) {
        Ok (
          toJson (
            Profile (
              bio = results.head._2,
              education = results.map(_._3).distinct,
              jobs = JobWithPositions.extract(results.map(r => (r._4, r._5)).distinct),
              skills = results.map(_._6).distinct,
              proficiencies = results.map(_._7).distinct
            )
          )
        )
      } else {
        NotFound        
      }
    }
  }
}

case class Profile (
  @ApiField(required = true)
  bio: Bio,
  @ApiField(required = true)
  education: Seq[Education],
  @ApiField(required = true)
  jobs: Seq[JobWithPositions],
  @ApiField(required = true)
  proficiencies: Seq[Proficiency],
  @ApiField(required = true)
  skills: Seq[Skill]
)