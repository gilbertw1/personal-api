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
    val query = for { ((u, j), p) <-
                      USER join
                      JOB on (_.id === _.userId) join
                      POSITION on (_._2.id === _.jobId)
                      if u.slug === slug } yield (j, p)
    db.run(query.result).map(JobWithPositions.extract)
  }

}
