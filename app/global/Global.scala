package global

import play.api.Play

object Globals {
  val shouldLoadStartupData = Play.current.configuration.getBoolean("startup-data.load").get
  val defaultUserSlug = Play.current.configuration.getString("default.user-slug").get
  val githubUsername = Play.current.configuration.getString("github.username").get
}
