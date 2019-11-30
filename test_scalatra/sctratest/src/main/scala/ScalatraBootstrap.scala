import uk.co.danrh.scc.proto._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new SharesServlet, "/share")
    context.mount(new HelloServlet, "/*")
    context.setInitParameter(CorsSupport.AllowCredentialsKey, "false")
    context.setInitParameter(CorsSupport.AllowedOriginsKey, "*")
//    context.setInitParameter(CorsSupport.AccessControlAllowCredentialsHeader, "false")
    // Optional because * is the default
//    context.initParameters("org.scalatra.cors.allowedOrigins") = "*"
    // Disables cookies, but required because browsers will not allow passing credentials to wildcard domains
//    context.initParameters("org.scalatra.cors.allowCredentials") = false
//    context.setInitParameter(CorsSupport.AllowCredentialsKey)
//    context.setInitParameter("org.scalatra.cors.allowCredentials","false")
  }
}
