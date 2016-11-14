package models

import io.swagger.annotations._
import annotation.meta.field

object Annotations {
  type ApiField = (ApiModelProperty @field)
}
