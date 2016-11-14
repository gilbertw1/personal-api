package sql

import models.Model
import scala.concurrent.{ ExecutionContext, Future }
import slick.driver.H2Driver.api._

abstract class SqlCompanion[T <: Model[_], U <: ApiTable[T]](val tquery: TableQuery[U]) {
  def self(): SqlCompanion[T, U] = this
}
