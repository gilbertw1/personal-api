import sbt._
import Keys._
import play.Project._

object Dependencies {
  val akkaVersion = "2.2.3"
  val playVersion = "2.2.2"

  val akka =  "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val playJdbc = "com.typesafe.play" %% "play-jdbc" % playVersion

  val playSwagger = "com.wordnik" %% "swagger-play2" % "1.3.2"
  val scalaAsync = "org.scala-lang.modules" %% "scala-async" % "0.9.0"
  val mysql = "mysql" % "mysql-connector-java" % "5.1.18"
  val slick = "com.typesafe.slick" %% "slick" % "2.0.0"
  val slf4j = "org.slf4j" % "slf4j-nop" % "1.6.4"

  val mainDeps = Seq(playJdbc, playSwagger, scalaAsync, akka, mysql, slick, slf4j)
}

object Resolvers {
  val typesafeRepo = "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
  val all = Seq(typesafeRepo)
}

object ApplicationBuild extends Build {

  val appName         = "personal-api"
  val appVersion      = "1.0-SNAPSHOT"

  val main = play.Project(appName, appVersion).settings(
    scalaVersion := "2.10.3",
    libraryDependencies ++= Dependencies.mainDeps,
    resolvers ++= Resolvers.all
  )

}
