package uk.co.danrh.scc.proto

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import uk.co.danrh.scc.bl.{ApiKeyBl, SharesBl}
import uk.co.danrh.scc.datatypes.{ApiKey, ResponseCode, Share}

class SharesServlet extends ScalatraServlet with JacksonJsonSupport with CorsSupport {

  options("/*"){
    response.setHeader("Access-Control-Allow-Origin", "true")//request.getHeader("Origin"))
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats


  before() {
    contentType = formats("json")
    val key = multiParams("key")
    if (key.isEmpty || !ApiKeyBl.checkKeyValid(ApiKey(key.head))) halt(status= 401)
  }


  post("/") {
    val share: Share = parsedBody.extract[Share]
    val responseCode = SharesBl.createShare(share)
    responseCode match {
      case ResponseCode.Failed(msg) => halt(status = 404, body = responseCode)
      case ResponseCode.Created(msg) => halt(status = 201, body = responseCode)
      case ResponseCode.Updated(msg) => halt(status = 200, body = responseCode)
    }
  }

  get("/:id") {
    SharesBl.getShare(params("id"))
  }

  get("/list/?") {
    SharesBl.getShares(10)
  }

  get("/convert/:c1/:c2") {
    SharesBl.getConversionRate(params("c1"),params("c2"))
  }


  get("/list/:number") {
    val searchterms = multiParams("st")
    if (searchterms.isEmpty) {
      SharesBl.getShares(params("number").toInt)
    } else {
      SharesBl.searchShares(params("number").toInt, searchterms)
    }
  }

  get("/currencies/?") {
    SharesBl.getCurrencies()
  }

  notFound {
    contentType = formats("html")
    <h1>404 Share Resource Not found.</h1>
  }
}
