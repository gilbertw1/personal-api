package sql

import models._

trait BioSqlModule { this: SqlModule with UserSqlModule =>
  import profile.simple._

  object Bios extends SqlCompanion[Bio,Bios](Bio,TableQuery[Bios]) {
    val BIOS = db

    def findOneByUserSlug(userSlug: String)(implicit session: Session): Option[Bio] = {
      val bioQuery = for (
        u <- Users.db if u.slug === userSlug;
        b <- Bios.db if b.userId === u.id
      ) yield (b)
      bioQuery.list.headOption
    }
  }

  class Bios(tag: Tag) extends PersonalApiTable[Bio](tag, "bio") {
    def userId = column[Long]("user_id")
    def firstname = column[String]("firstname")
    def lastname = column[String]("lastname")
    def middlename = column[String]("middlename", O.Nullable)
    def suffix = column[String]("suffix", O.Nullable)
    def title = column[String]("title", O.Nullable)
    def profile = column[String]("profile", O.Nullable)
    def email = column[String]("email", O.Nullable)
    def phone = column[String]("phone", O.Nullable)
    def githubUsername = column[String]("github_username", O.Nullable)
    def twitterUsername = column[String]("twitter_username", O.Nullable)
    def linkedinUsername = column[String]("linkedin_username", O.Nullable)
    def * = (id.?, userId, firstname, lastname, middlename.?,
              suffix.?, title.?, profile.?, email.?, phone.?,
              githubUsername.?, twitterUsername.?, linkedinUsername.?) <> ((Bio.apply _).tupled, Bio.unapply _)

    def user = foreignKey("bio_user_fk", userId, Users.db)(_.id)

    override def filterableColumnByName = Map[String,FilterableColumn[_]] (
      "userId" -> FilterableColumn(userId, LongField),
      "firstname" -> FilterableColumn(firstname, StringField),
      "lastname" -> FilterableColumn(lastname, StringField),
      "middlename" -> FilterableColumn(middlename, StringField),
      "suffix" -> FilterableColumn(suffix, StringField),
      "title" -> FilterableColumn(title, StringField),
      "profile" -> FilterableColumn(profile, StringField),
      "email" -> FilterableColumn(email, StringField),
      "phone" -> FilterableColumn(phone, StringField),
      "githubUsername" -> FilterableColumn(githubUsername, StringField),
      "twitterUsername" -> FilterableColumn(twitterUsername, StringField),
      "linkedinUsername" -> FilterableColumn(linkedinUsername, StringField)
    )
  }
}