package models

import Annotations._
import play.api.libs.json.Json

object Profile {
  private implicit val bioFmt = Bio.format
  private implicit val educationFmt = Education.format
  private implicit val jobWithPositionsFmt = JobWithPositions.format
  private implicit val proficiencyFmt = Proficiency.format
  private implicit val skillFmt = Skill.format
  implicit val format = Json.format[Profile]

  def extract(results: Seq[(User, Bio, Education, Job, Position, Skill, Proficiency)]): Option[Profile] = {
    println("Extracting Results: " + results)
    if (results.nonEmpty) {
      Some(
        Profile (
          bio = results.head._2,
          education = results.map(_._3).distinct,
          jobs = JobWithPositions.extract(results.map(r => (r._4, r._5)).distinct),
          skills = results.map(_._6).distinct,
          proficiencies = results.map(_._7).distinct)
      )
    } else {
      None
    }
  }
}

case class Profile (
  @ApiField(required = true)
    bio: Bio,
  @ApiField(required = true)
    education: Seq[Education],
  @ApiField(required = true)
    jobs: Seq[JobWithPositions],
  @ApiField(required = true)
    proficiencies: Seq[Proficiency],
  @ApiField(required = true)
    skills: Seq[Skill]
)
