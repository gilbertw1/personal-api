package models

import com.wordnik.swagger.annotations._
import annotation.target.field

object Annotations {
  type ApiField = (ApiModelProperty @field)
}