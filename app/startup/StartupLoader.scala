package startup

import models._
import services.{ ApiServices, DatabaseService }
import global.Globals

import javax.inject._
import java.io.File
import scala.io.Source
import scala.concurrent.{ Future, Await }
import scala.concurrent.duration._

import play.api.Play

class StartupLoader @Inject() (apiServices: ApiServices) {

  println("RUNNING STARTUP LOADER")

  // TODO: Abosolutely Horrendous
  Future({
    Thread.sleep(5000L)
    if (Globals.shouldLoadStartupData) {
      val dir = Play.current.configuration.getString("startup-data.directory").get
      loadStartupData(dir, apiServices.userService, User)
      loadStartupData(dir, apiServices.bioService, Bio)
      loadStartupData(dir, apiServices.jobService, Job)
      loadStartupData(dir, apiServices.positionService, Position)
      loadStartupData(dir, apiServices.educationService, Education)
      loadStartupData(dir, apiServices.skillService, Skill)
      loadStartupData(dir, apiServices.proficiencyService, Proficiency)
    }
  })(scala.concurrent.ExecutionContext.global)

  def loadStartupData[T <: Model[_]](dir: String, service: DatabaseService[T,_], companion: ModelCompanion[T]) {
    import play.api.libs.concurrent.Execution.Implicits._
    println("Loading data for: " + companion.name)
    val resourceFile = getResourceFile(dir, companion.name)
    val loadedModels = resourceFile.map(_.getLines.toList).getOrElse(List[String]()).map(companion.fromJson)
    try {
      loadedModels.foreach(m => Await.result(service.insert(m), 10.seconds))
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  def getResourceFile(dir: String, model: String): Option[Source] = {
    if (dir.startsWith("$resources")) {
      val relative = dir.replaceAll("\\$resources(/|\\\\)", "")
      Play.current.resource(s"${relative}/${model}.json").map(Source.fromURL)
    } else {
      val file = new File(s"${dir}/${model}.json")
      if(file.exists) Some(Source.fromFile(file)) else None
    }
  }
}
