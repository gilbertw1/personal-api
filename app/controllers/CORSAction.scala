package controllers

import scala.concurrent._
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object CORSAction {

  def apply(block: Request[AnyContent] => Future[Result]): Action[AnyContent] = {
    Action.async { request =>
      block(request).map(_.withHeaders("Access-Control-Allow-Origin" -> "*"))
    }
  }

  def apply(block: => Future[Result]): Action[AnyContent] = {
    this.apply(_ => block)
  }
}