package controllers

import global.Globals
import play.api._
import play.api.mvc._

class Application extends Controller {

  def swagger = Action {
    Ok(views.html.index(Globals.githubUsername))
  }

  def alo = Action {
    Ok("alo")
  }
}
