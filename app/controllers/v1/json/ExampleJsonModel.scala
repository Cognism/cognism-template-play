package controllers.v1.json

import io.swagger.annotations.{ApiModelProperty, ApiModel}


/**
  * Created by stipe on 21.12.2015..
  */

@ApiModel
case class ExampleModel(
  @ApiModelProperty(required = true) key: String,
  @ApiModelProperty(required = true) value: String,
  @ApiModelProperty(required = true) active: Boolean
)
