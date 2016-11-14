package services

import sql._
import models.Profile

import scala.concurrent.{ Future, ExecutionContext }
import java.util.UUID
import slick.driver.H2Driver.api._

import Users._
import Bios._
import Jobs._
import Positions._
import Educations._
import Skills._
import Proficiencies._

class UserService(database: Database) extends DatabaseService(Users, database) {

  def findProfileByUserSlug(slug: String)(implicit ec: ExecutionContext): Future[Option[Profile]] = {
    val query = for {
      u <- USER if u.slug === slug
      b <- BIO if b.userId === u.id
      e <- EDUCATION if e.userId === u.id
      j <- JOB if j.userId === u.id
      p <- POSITION if p.jobId === j.id
      s <- SKILL if s.userId === u.id
      pr <- PROFICIENCY if pr.userId === u.id } yield (u, b, e, j, p, s, pr)

    db.run(query.result).map(Profile.extract)
  }
}
