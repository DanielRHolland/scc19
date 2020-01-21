package uk.co.danrh.scc.proto

import org.scalatra.ScalatraServlet

class HelloServlet extends ScalatraServlet {
  get("/hello") {
    <p> Hello there </p>//views.html.hello()
  }

  get("/hello/:name") {
    <p>Hello, {params("name")}</p>
  }

  get("/"){
    <div>
    <p> Routes: </p>
      <ul>
        <li>/user</li>
          <ul>
            <li>/login GET</li>
            <li>/create PUT</li>
          </ul>
        <li>/share</li>
        <ul>
          <li>/ POST</li>
          <li>/id/:id GET</li>
          <li>/list GET</li>
          <li>/user/u/:id GET</li>
          <li>/user GET</li>
          <li>/purchase POST</li>
        </ul>
      </ul>
    </div>
  }

  notFound {
    <h1>404 Not found.</h1>
  }
}
