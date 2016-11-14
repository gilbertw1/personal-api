package sql

import models.Model
import java.util.UUID
import slick.driver.H2Driver.api._

abstract class ApiTable[T <: Model[_]](tag: Tag, tname: String) extends Table[T](tag, tname) {
  def id = column[UUID]("id", O.PrimaryKey)
}
