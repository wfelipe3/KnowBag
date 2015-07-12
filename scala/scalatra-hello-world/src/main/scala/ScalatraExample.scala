import org.scalatra._

/**
 * Created by feliperojas on 7/5/15.
 */
class ScalatraExample extends ScalatraServlet {

  get("/") {
    <h1>Hello, world!</h1>
  }
}
