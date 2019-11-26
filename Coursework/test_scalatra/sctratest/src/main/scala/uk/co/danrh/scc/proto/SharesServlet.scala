package uk.co.danrh.scc.proto

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
import uk.co.danrh.scc.bl.SharesBl
import uk.co.danrh.scc.datatypes.ResponseCode.ResponseCode
import uk.co.danrh.scc.datatypes.{ResponseCode, Share}

class SharesServlet extends ScalatraServlet with JacksonJsonSupport{

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  post("/") {
    val share: Share = parsedBody.extract[Share]
    val responseCode = SharesBl.createShare(share)
    responseCode match {
      case ResponseCode.Failed => halt(status = 404, body = "Failed")
      case ResponseCode.Created => halt(status = 201, body = "Success. Created: " + share.companySymbol)
      case ResponseCode.Updated => halt(status = 200, body = "Success. Updated: " + share.companySymbol)
    }
  }

//  put("/:id") {
//    val share: Share = parsedBody.extract[Share]
//    println(share.sharePrice.currency)
//    println(share.companySymbol)
//    halt(status = 201, body = "Success. Updated: " + share.companySymbol)
//  }

  get("/:id") {
    SharesBl.getShare(params("id"))
  }

  get("/list/?") {
    SharesBl.getShares(10)
  }

  get("/list/:number") {
    SharesBl.getShares(params("number").toInt)
  }

  notFound {
    contentType = formats("html")
    <h1>404 Share Resource Not found.</h1>
  }
}
