package services

import sql.{ Proficiencies, Users }
import models.Proficiency

import scala.concurrent.Future
import java.util.UUID
import slick.driver.H2Driver.api._

import Proficiencies._
import Users._

class ProficiencyService(database: Database) extends DatabaseService(Proficiencies, database) {

  def findByUserSlug(slug: String): Future[Seq[Proficiency]] = {
    val query = for {
      u <- USER if u.slug === slug
      p <- PROFICIENCY if u.id === p.userId } yield (p)

    db.run(query.result)
  }
}
