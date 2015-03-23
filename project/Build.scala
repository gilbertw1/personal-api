import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object Dependencies {
  val akkaVersion = "2.3.9"

  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaRemote = "com.typesafe.akka" %% "akka-remote" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion

  val scalaAsync = "org.scala-lang.modules" %% "scala-async" % "0.9.2"
  val playSwagger = "com.wordnik" %% "swagger-play2" % "1.3.12"
  val playJdbc = "com.typesafe.play" %% "play-jdbc" % "2.3.8"
  val mysql = "mysql" % "mysql-connector-java" % "5.1.34"
  val slick = "com.typesafe.slick" %% "slick" % "2.1.0"

  val akkaDeps = Seq(akkaActor, akkaRemote, akkaSlf4j, akkaTestkit)
  val miscDeps = Seq(playSwagger, scalaAsync, mysql, slick, playJdbc)
  val mainDeps = akkaDeps ++ miscDeps
}

object Resolvers {
  val typesafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val all = Seq(typesafeRepo)
}

object ApplicationBuild extends Build {

  val appName         = "personal-api"
  val appVersion      = "1.0-SNAPSHOT"

  val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
    scalaVersion := "2.11.6",
    libraryDependencies ++= Dependencies.mainDeps,
    resolvers ++= Resolvers.all
  )

}
