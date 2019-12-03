package uk.co.danrh.scc.proto

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import uk.co.danrh.scc.bl.SharesBl
import uk.co.danrh.scc.datatypes.{ResponseCode, Share}

class SharesServlet extends ScalatraServlet with JacksonJsonSupport with CorsSupport {

  options("/*"){
    response.setHeader("Access-Control-Allow-Origin", "true")//request.getHeader("Origin"))
  }

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  case class stat(stat:String)
  post("/") {
    val share: Share = parsedBody.extract[Share]
    val responseCode = SharesBl.createShare(share)
    responseCode match {
      case ResponseCode.Failed => halt(status = 404, body = stat("Failed"))
      case ResponseCode.Created => halt(status = 201, body = stat("Success. Created: " + share.companySymbol))
      case ResponseCode.Updated => halt(status = 200, body = stat("Success. Updated: " + share.companySymbol))
    }
  }

  get("/:id") {
    SharesBl.getShare(params("id"))
  }

  get("/list/?") {
    SharesBl.getShares(10)
  }

  get("/list/:number") {
    val searchterms = multiParams("st")
    if (searchterms.isEmpty) {
      SharesBl.getShares(params("number").toInt)
    } else {
      SharesBl.searchShares(params("number").toInt, searchterms)
    }
  }

  get("/currencies") {
    SharesBl.getCurrencies()
  }

  notFound {
    contentType = formats("html")
    <h1>404 Share Resource Not found.</h1>
  }
}
