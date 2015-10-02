import java.io.{FileWriter, PrintWriter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.io.Source
import scala.util.{Failure, Success}
import scala.concurrent.duration._

/**
 * Created by feliperojas on 9/10/15.
 */
object AsyncLogger {

  val path = "/Users/feliperojas/KnowBag/scala/play-with-fuures/src/test/resources/test.txt"

  def log(message: String): Unit = {

    (1 to 10) foreach { value =>
      Future {
        val writer = new PrintWriter(new FileWriter(path, true), true)
        writer.println(message + value)
      }.onComplete {
        case Success(completeMessage) => println(completeMessage)
        case Failure(e) => e.printStackTrace()
      }
    }

  }

}
