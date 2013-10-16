package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def swagger = Action {
    Ok(views.html.index())
  }

  def alo = Action {
    Ok("alo")
  }
  
}