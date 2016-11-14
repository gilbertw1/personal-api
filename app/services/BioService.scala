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
    val query = for { (b, u) <-
                      BIO join
                      USER on (_.userId === _.id)
                        if u.slug === slug } yield (b)

    db.run(query.result.headOption)
  }
}
