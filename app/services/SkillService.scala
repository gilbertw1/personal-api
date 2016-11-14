package services

import models.Skill
import sql.{ Skills, Users }

import scala.concurrent.Future
import java.util.UUID
import slick.driver.H2Driver.api._

import Skills._
import Users._

class SkillService(database: Database) extends DatabaseService(Skills, database) {

  def findByUserSlug(slug: String): Future[Seq[Skill]] = {
    val query = for {
      u <- USER if u.slug === slug
      s <- SKILL if s.userId === u.id } yield (s)

    db.run(query.result)
  }
}
