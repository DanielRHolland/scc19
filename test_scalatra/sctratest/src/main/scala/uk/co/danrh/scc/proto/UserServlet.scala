package uk.co.danrh.scc.proto

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.{CorsSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport
import uk.co.danrh.scc.bl.{ApiKeyBl, UsersBl}
import uk.co.danrh.scc.datatypes.{ResponseCode, User}

class UserServlet extends ScalatraServlet with JacksonJsonSupport with CorsSupport {

  options("/*") {
    response.setHeader("Access-Control-Allow-Origin", "true") //request.getHeader("Origin"))
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/login/?") {
    ApiKeyBl.createKey(params("id"),params("hash")) match {
      case Right(apiKey) => apiKey
      case Left(failure) => halt(status = 405, body = failure)
    }
  }

  put("/create/?") {
    val responseCode = UsersBl.createUser(parsedBody.extract[User])
    responseCode match {
      case ResponseCode.Failed(msg, obj) => halt(status = 404, body = responseCode)
      case ResponseCode.Updated(msg, obj) => halt(status = 201, body = responseCode)
      case ResponseCode.Created(msg, obj) => halt(status = 201, body = responseCode)
    }
  }
}