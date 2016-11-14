package services

import sql.Positions
import java.util.UUID
import slick.driver.H2Driver.api._

import Positions._

class PositionService(database: Database) extends DatabaseService(Positions, database) { }
