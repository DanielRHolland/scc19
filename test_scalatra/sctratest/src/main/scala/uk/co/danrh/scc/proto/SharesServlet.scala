package uk.co.danrh.scc.proto

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._
import uk.co.danrh.scc.bl.{ApiKeyBl, SharesBl}
import uk.co.danrh.scc.datatypes.{ApiKey, Purchase, ResponseCode, SearchOptions, Share}

class SharesServlet extends ScalatraServlet with JacksonJsonSupport with CorsSupport {

  options("/*"){
    response.setHeader("Access-Control-Allow-Origin", "true")//request.getHeader("Origin"))
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats


  before() {
    contentType = formats("json")
    val key = multiParams("key")
    //if (key.isEmpty || !ApiKeyBl.checkKeyValid(ApiKey(key.head))) halt(status= 401)
  }


  post("/") {
    val share: Share = parsedBody.extract[Share]
    val responseCode = SharesBl.createShare(share)
    responseCode match {
      case ResponseCode.Failed(msg, obj) => halt(status = 404, body = responseCode)
      case ResponseCode.Created(msg, obj) => halt(status = 201, body = responseCode)
      case ResponseCode.Updated(msg, obj) => halt(status = 200, body = responseCode)
    }
  }

  get("/id/:id") {
    SharesBl.getShare(params("id"))
  }

  get("/list/?") {
    SharesBl.getShares(parseSearchOptions)
  }

  get("/symbols/?") {
    SharesBl.getSymbols(1000)
  }

  get("/convert/:c1/:c2") {
    SharesBl.getConversionRate(params("c1"),params("c2"))
  }


  get("/list/:number") {
    SharesBl.getShares(parseSearchOptions)
  }

  get("/currencies/?") {
    SharesBl.getCurrencies
  }

  get("/user/u/:id") {
    SharesBl.getUserIdShareQuantities(params("id"))
  }

  get("/user") {
    val userId = ApiKeyBl.getUserId(multiParams("key").head)
    val searchOptions: SearchOptions = parseSearchOptions
    SharesBl.getUserIdShareQuantities(userId, searchOptions)
  }

  private def parseSearchOptions =
    SearchOptions(
      if (multiParams("number").isEmpty) 10 else (multiParams("number").head.toInt),
      multiParams("st").toList,
      if (multiParams("ob").isEmpty) "default" else multiParams("ob").head
    )

  post("/purchase/?") {
    val purchase = parsedBody.extract[Purchase]
    val responseCode: ResponseCode = SharesBl.purchase(ApiKeyBl.getUserId(multiParams("key").head), purchase)
    responseCode match {
      case ResponseCode.Created(msg, obj) => halt(201, responseCode)
      case ResponseCode.Updated(msg, obj) => halt(200, responseCode)
      case ResponseCode.Failed(msg, obj) => halt(401, responseCode)
    }
  }

  delete("/usershare") {
    val userId = ApiKeyBl.getUserId(multiParams("key").head)
    val shareId = params("id")
    SharesBl.deleteUserShare(userId, shareId)
  }

  notFound {
    contentType = formats("html")
    <h1>404 Share Resource Not found.</h1>
  }
}
