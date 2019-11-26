package uk.co.danrh.scc.proto

import org.scalatra.ScalatraServlet

class HelloServlet extends ScalatraServlet {
  get("/hello") {
    views.html.hello()
  }

  get("/hello/:name") {
    <p>Hello, {params("name")}</p>
  }

  get("/"){
    <p> Hello there </p>
  }

  notFound {
    <h1>404 Not found.</h1>
  }
}
