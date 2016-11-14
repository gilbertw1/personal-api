package sql

import models.Proficiency

import java.util.UUID
import slick.driver.H2Driver.api._

object Proficiencies
    extends SqlCompanion[Proficiency,Proficiencies](TableQuery[Proficiencies]) {
  val PROFICIENCY = tquery
}

class Proficiencies(tag: Tag) extends ApiTable[Proficiency](tag, "proficiency") {
  def userId = column[UUID]("user_id")
  def title = column[String]("title")
  def * = (id, userId, title) <> ((Proficiency.apply _).tupled, Proficiency.unapply _)
}
