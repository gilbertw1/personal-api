package controllers

import models.Bio
import play.api._
import play.api.mvc._
import play.api.libs.json.{Json,Format,JsNumber,JsValue,JsSuccess,JsNull,JsResult}
import Json._
import util.{Global,JsonHelper}
import com.wordnik.swagger.core._
import com.wordnik.swagger.annotations._

import JsonHelper._

@Api(value = "/bio", listingPath = "/api-docs/bio", description = "Biographical Information")
object BioController extends Controller {

  implicit val bioFormat = format[Bio]

  @ApiOperation(value = "Retrieves Basic Bio Info", responseClass = "models.Bio", httpMethod = "GET")
  def get = CORSAction {
    Ok (
      stringify (
        toJson (
          Bio.findOneByUserId(Global.userId)
        )
      )
    )
  }
  
}