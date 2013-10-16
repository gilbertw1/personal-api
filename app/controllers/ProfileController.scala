package controllers

import models.{Bio,Education,Position,Job,Skill,Proficiency}
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/profile", listingPath = "/api-docs/profile", description = "Full Profile")
object ProfileController extends Controller {

  implicit val bioFormat = format[Bio]
  implicit val educationFormat = format[Education]
  implicit val positionFormat = format[Position]
  implicit val jobFormat = format[Job]
  implicit val proficiencyFormat = format[Proficiency]
  implicit val skillFormat = format[Skill]
  implicit val profileFormat = format[Profile]

  @ApiOperation(value = "Returns entire profile", responseClass = "controllers.Profile", httpMethod = "GET")
  def get = Action {
    Ok (
      stringify (
        toJson (
          Profile (
            bio = Bio.findOneByUserId(Global.userId).get,
            education = Education.findByUserId(Global.userId),
            jobs = Job.findByUserId(Global.userId),
            proficiencies = Proficiency.findByUserId(Global.userId),
            skills = Skill.findByUserId(Global.userId)
          )
        )
      )
    )
  }
  
}

case class Profile (
  bio: Bio,
  education: List[Education],
  jobs: List[Job],
  proficiencies: List[Proficiency],
  skills: List[Skill]
)