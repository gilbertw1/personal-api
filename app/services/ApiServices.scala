package services

import javax.inject._
import scala.concurrent.ExecutionContext
import slick.driver.H2Driver
import slick.driver.H2Driver.api._
import play.api.db.slick.DatabaseConfigProvider

class ApiServices @Inject() (dbConfigProvider: DatabaseConfigProvider) {
  private val dbConfig = dbConfigProvider.get[H2Driver]
  private val db = dbConfig.db

  val bioService = new BioService(db)
  val educationService = new EducationService(db)
  val jobService = new JobService(db)
  val positionService = new PositionService(db)
  val proficiencyService = new ProficiencyService(db)
  val skillService = new SkillService(db)
  val userService = new UserService(db)
}
