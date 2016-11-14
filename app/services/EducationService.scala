package services

import models.Education
import sql.{ Educations, Users }

import scala.concurrent.Future
import java.util.UUID
import slick.driver.H2Driver.api._

import Educations._
import Users._

class EducationService(database: Database) extends DatabaseService(Educations, database) {

  def findByUserSlug(slug: String): Future[Seq[Education]] = {
    val query = for {
      u <- USER if u.slug === slug
      e <- EDUCATION if e.userId === u.id } yield (e)

    db.run(query.result)
  }
}
