package uk.co.danrh.scc.standalone_launcher
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.ScalatraBase
import org.scalatra.servlet.ScalatraListener

object JettyLauncher { // this is my entry object as specified in sbt project definition
  def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080
    val server = new Server(port)
    val context = new WebAppContext()
    context setContextPath "/"
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)
    context.addServlet(classOf[DefaultServlet], "/")
    context.setInitParameter(org.scalatra.EnvironmentKey, "production")
    context.setInitParameter(ScalatraBase.HostNameKey, "impresst.uk")
    context.setInitParameter(ScalatraBase.ForceHttpsKey, "true")
    server.setHandler(context)
    server.start
    server.join
  }
}
