package controllers

import global.Globals
import play.api._
import play.api.mvc._

object Application extends Controller {

  def swagger = Action {
    Ok(views.html.index(Globals.swaggerBase))
  }

  def alo = Action {
    Ok("alo")
  }
  
}