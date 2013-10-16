package controllers

import models.Education
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/education", listingPath = "/api-docs/education", description = "Education History Information")
object EducationController extends Controller {

  implicit val educationFormat = format[Education]

  @ApiOperation(value = "Retrieves Education History", responseClass = "List[models.Education]", httpMethod = "GET")
  def get = Action {
    Ok (
      stringify (
        toJson (
          Education.findByUserId(Global.userId)
        )
      )
    )
  }
  
}