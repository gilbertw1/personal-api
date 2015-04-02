package sql

import models._

trait SkillSqlModule { this: SqlModule with UserSqlModule =>
  import profile.simple._

  object Skills extends SqlCompanion[Skill,Skills](Skill,TableQuery[Skills]) {
    val SKILLS = db

    def findByUserSlug(userSlug: String)(implicit session: Session): Seq[Skill] = {
      val skillQuery = for (
        u <- Users.db if u.slug === userSlug;
        s <- Skills.db if s.userId === u.id
      ) yield (s)
      skillQuery.list
    }
  }

  class Skills(tag: Tag) extends PersonalApiTable[Skill](tag, "skill") {
    def userId = column[Long]("user_id")
    def title = column[String]("title")
    def description = column[String]("description")
    def * = (id.?, userId, title, description) <> ((Skill.apply _).tupled, Skill.unapply _)

    def user = foreignKey("skill_user_fk", userId, Users.db)(_.id)

    override def filterableColumnByName = Map[String,FilterableColumn[_]] (
      "userId" -> FilterableColumn(userId, LongField),
      "title" -> FilterableColumn(title, StringField),
      "description" -> FilterableColumn(description, StringField)
    )
  }
}