package knowbag.snippets.snippets

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Created by feliperojas on 6/04/15.
 */
class FuturesLearn extends FlatSpec with Matchers {

  "Futures can invoke logic and then be called to get the value, they need the implicit global import if not, " +
    "an implicit execution context should be provided" should "be asynchronous" in {
    val future = Future {
      println("this is a test")
      "value"
    }
    future.onSuccess {
      case success: String => println(s"nice $success")
    }
    future.onFailure {
      case th: Throwable => println(s"error $th")
    }
    println("I am here")
  }

}
