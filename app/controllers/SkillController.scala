package controllers

import models.Skill
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/skills", listingPath = "/api-docs/skills", description = "Skill Information")
object SkillController extends Controller {

  implicit val skillFormat = format[Skill]

  @ApiOperation(value = "Retrieves List of Skills", responseClass = "List[models.Skill]", httpMethod = "GET")
  def get = CORSAction {
    Ok (
      stringify (
        toJson (
          Skill.findByUserId(Global.userId)
        )
      )
    )
  }
  
}