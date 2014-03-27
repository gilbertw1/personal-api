package global

import models._
import sql._

import java.io.File
import scala.io.Source
import scala.concurrent.ExecutionContext
import scala.slick.driver.MySQLDriver.simple.{Database,Session => DBSession}

import play.api._
import play.api.db._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import com.typesafe.config.ConfigFactory

object Globals {
  val shouldLoadStartupData = Play.configuration.getBoolean("startup-data.load").get
  val dbDriver = Play.configuration.getString("db.default.driver").get
  val defaultUserSlug = Play.current.configuration.getString("default.user-slug").get
  val swaggerBase = Play.current.configuration.getString("swagger.api.basepath").get

  val db = Database.forDataSource(DB.getDataSource())
  val sqlContext: ExecutionContext = Akka.system.dispatchers.lookup("contexts.sql-pool")
  val databaseModule = dbDriver match {
    case "org.h2.Driver" => H2SqlModule
    case "com.mysql.jdbc.Driver" => MysqlModule
  }
}

import Globals._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    if (shouldLoadStartupData) {
      loadStartupData(app)     
    }
  }

  def loadStartupData(app: Application) {
    val dir = app.configuration.getString("startup-data.directory").get
    Globals.db.withSession { implicit session =>
      loadStartupData(dir, User)
      loadStartupData(dir, Bio)
      loadStartupData(dir, Education)
      loadStartupData(dir, Job)
      loadStartupData(dir, Position)
      loadStartupData(dir, Skill)
      loadStartupData(dir, Proficiency)
    }
  }

  def loadStartupData[T <: Model](dir: String, companion: ModelCompanion[T])(implicit session: DBSession) {
    import databaseModule.profile.simple._
    val sqlCompanion = databaseModule.sqlCompanionFor(companion)
    println("Loading data for: " + companion.name)
    val resourceFile = getResourceFile(dir, companion.name)
    val loadedModels = resourceFile.map(_.getLines.toList).getOrElse(List[String]()).map(companion.fromJson)
    loadedModels.foreach(sqlCompanion.insert)
  }

  def getResourceFile(dir: String, model: String)(implicit app: Application): Option[Source] = {
    if (dir.startsWith("$resources")) {
      val relative = dir.replaceAll("\\$resources(/|\\\\)", "")
      app.resource(s"${relative}/${model}.json").map(Source.fromURL)
    } else {
      val file = new File(s"${dir}/${model}.json")
      if(file.exists) Some(Source.fromFile(file)) else None
    }
  }
}