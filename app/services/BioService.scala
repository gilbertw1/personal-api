package services

import models.Bio
import sql.{ Bios, Users }
import scala.concurrent.Future
import java.util.UUID
import slick.driver.H2Driver.api._

import Bios._
import Users._

class BioService(database: Database) extends DatabaseService(Bios, database) {

  def findOneByUserSlug(slug: String): Future[Option[Bio]] = {
    val query = for {
      u <- USER if u.slug === slug
      b <- BIO if b.userId === u.id } yield (b)

    db.run(query.result.headOption)
  }
}
