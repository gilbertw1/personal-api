package models

import com.wordnik.swagger.annotations._
import annotation.meta.field

object Annotations {
  type ApiField = (ApiModelProperty @field)
}