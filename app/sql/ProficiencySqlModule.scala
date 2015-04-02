package sql

import models._

trait ProficiencySqlModule { this: SqlModule with UserSqlModule =>
  import profile.simple._

  object Proficiencies extends SqlCompanion[Proficiency,Proficiencies](Proficiency,TableQuery[Proficiencies]) {
    val PROFICIENCIES = db

    def findByUserSlug(userSlug: String)(implicit session: Session): Seq[Proficiency] = {
      val proficiencyQuery = for (
        u <- Users.db if u.slug === userSlug;
        p <- Proficiencies.db if p.userId === u.id
      ) yield (p)
      proficiencyQuery.list
    }
  }

  class Proficiencies(tag: Tag) extends PersonalApiTable[Proficiency](tag, "proficiency") {
    def userId = column[Long]("user_id")
    def title = column[String]("title")
    def * = (id.?, userId, title) <> ((Proficiency.apply _).tupled, Proficiency.unapply _)

    def user = foreignKey("proficiency_user_fk", userId, Users.db)(_.id)

    override def filterableColumnByName = Map[String,FilterableColumn[_]] (
      "userId" -> FilterableColumn(userId, LongField),
      "title" -> FilterableColumn(title, StringField)
    )
  }
}