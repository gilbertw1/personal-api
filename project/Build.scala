import sbt._
import Keys._
import play.sbt._
import Play.autoImport._
import PlayKeys._

object BuildSettings {
  val buildOrganization = "com.bryangilbert"
  val buildVersion      = "0.1.0"
  val buildScalaVersion = "2.11.8"

  val buildSettings = Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion
  )
}

object Dependencies {
  val akkaVersion = "2.4.12"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  val playSlick = "com.typesafe.play" %% "play-slick" % "2.0.0"
  val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0"
  val h2Driver = "com.h2database" % "h2" % "1.4.193"

  val scalaAsync = "org.scala-lang.modules" %% "scala-async" % "0.9.6"
  val playSwagger = "io.swagger" %% "swagger-play2" % "1.5.3"
  val slick = "com.typesafe.slick" %% "slick" % "3.1.1"
  val slickJoda = "com.github.tototoshi" %% "slick-joda-mapper" % "2.2.0"

  val joda = "joda-time" % "joda-time" % "2.7"
  val jodaConvert = "org.joda" % "joda-convert" % "1.7"

  val akkaDeps = Seq(akkaActor, akkaTestkit)
  val miscDeps = Seq(playSwagger, scalaAsync, slick, slickJoda, joda, jodaConvert, playSlick, playSlickEvolutions, h2Driver)
  val mainDeps = akkaDeps ++ miscDeps
}

object Resolvers {
  val typesafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val all = Seq(typesafeRepo)
}

object ApplicationBuild extends Build {
  import Resolvers._
  import BuildSettings._
  import Defaults._

  val appName = "personal-api"

  val api = Project(appName, file(".")).enablePlugins(PlayScala)
    .settings(buildSettings: _*)
    .settings(resolvers ++= Resolvers.all)
    .settings(libraryDependencies ++= Dependencies.mainDeps)
}
