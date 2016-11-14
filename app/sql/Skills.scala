package sql

import models.Skill
import java.util.UUID
import slick.driver.H2Driver.api._


object Skills extends SqlCompanion[Skill,Skills](TableQuery[Skills]) {
  val SKILL = tquery
}

class Skills(tag: Tag) extends ApiTable[Skill](tag, "skill") {
  def userId = column[UUID]("user_id")
  def title = column[String]("title")
  def description = column[String]("description")
  def * = (id, userId, title, description) <> ((Skill.apply _).tupled, Skill.unapply _)
}
