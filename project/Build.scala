import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "personal-api"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.wordnik" %% "swagger-play2" % "1.2.5",
    "mysql" % "mysql-connector-java" % "5.1.26",
    jdbc,
    anorm
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
