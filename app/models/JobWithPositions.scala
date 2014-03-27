package models

import Annotations._

object JobWithPositions {
  def extract(jobPositionTuples: Seq[(Job,Position)]): Seq[JobWithPositions] = {
    jobPositionTuples.groupBy(_._1).toSeq map { case (job, tuples) =>
      JobWithPositions(job, tuples.map(_._2))
    }
  }
}

case class JobWithPositions (
  @ApiField(required = true)
  job: Job, 
  @ApiField(required = true)
  positions: Seq[Position]
)