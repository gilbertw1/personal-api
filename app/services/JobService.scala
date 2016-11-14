package services

import sql.{ Jobs, Users, Positions }
import models.JobWithPositions

import scala.concurrent.{ Future, ExecutionContext }
import java.util.UUID
import slick.driver.H2Driver.api._

import Jobs._
import Users._
import Positions._

class JobService(database: Database) extends DatabaseService(Jobs, database) {

  def findJobsWithPositionsBySlug(slug: String)(implicit ec: ExecutionContext): Future[Seq[JobWithPositions]] = {
    val query = for {
      u <- USER if u.slug === slug
      j <- JOB if j.userId === u.id
      p <- POSITION if p.jobId === j.id } yield (j, p)

    db.run(query.result).map(JobWithPositions.extract)
  }

}
