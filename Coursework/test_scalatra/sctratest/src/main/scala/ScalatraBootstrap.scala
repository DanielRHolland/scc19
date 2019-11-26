import uk.co.danrh.scc.proto._
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new SharesServlet, "/share")
    context.mount(new HelloServlet, "/*")
  }
}
