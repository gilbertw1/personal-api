package controllers

import models.Proficiency
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/proficiencies", listingPath = "/api-docs/proficiencies", description = "Proficiency Information")
object ProficiencyController extends Controller {

  implicit val proficiencyFormat = format[Proficiency]

  @ApiOperation(value = "Retrieves List of Proficiencies", responseClass = "List[models.Proficiency]", httpMethod = "GET")
  def get = Action {
    Ok (
      stringify (
        toJson (
          Proficiency.findByUserId(Global.userId)
        )
      )
    )
  }
  
}