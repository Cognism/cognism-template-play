package controllers.v1

import controllers.Secured
import controllers.v1.json._
import io.swagger.annotations._
import play.api.mvc.{Action, Controller}

/**
  * Created by stipe on 21.12.2015..
  */
@Api(value="Example API v1")
class ExampleController extends Controller with Secured{

  @ApiOperation(value = "Noop")
  @ApiResponses(Array(
    new ApiResponse(code=200, message = "Ok")
  ))
  @ApiImplicitParams(Array(
    new ApiImplicitParam (name = "X-API-Token", value = "API Token", required = true, dataType = "string", paramType = "header"),
    new ApiImplicitParam(name="body", value="Payload", required = true, paramType = "body", dataType = "controllers.v1.json.ExampleModel")
  ))
  def noop = ApiAction(parse.json) { implicit request =>
    Ok("NOT IMPLEMENTED")
  }

}
