package util

import play.api.{GlobalSettings,Application,Play}
import models.User

object Global extends GlobalSettings {

  val userSlug = Play.current.configuration.getString("user.slug").get
  lazy val userId = User.findBySlug(userSlug).get.id.get

  override def onStart(app: Application) {
    /* Force Evaluation of Lazy User ID */
    val forceUserId = userId
  }

}