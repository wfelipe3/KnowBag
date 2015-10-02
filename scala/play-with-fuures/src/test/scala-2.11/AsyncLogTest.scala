import java.io.File
import java.util.{Timer, TimerTask}

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 9/10/15.
 */
class AsyncLogTest extends FlatSpec with Matchers {

  val path: String = "/Users/feliperojas/KnowBag/scala/play-with-fuures/src/test/resources/test.txt"

  behavior of "Async log"

  it should "log value to a file asynchronous way" in {
    val space = new File(path).length()

    checkFile(path)({ value =>
      println(s"the file has ${value - space} more bytes")
    })

    AsyncLogger.log("this is a test")
    AsyncLogger.log("this is a test 2")

    Thread.currentThread().join(1000)
  }

  private def checkFile(dir: String)(checker: Long => Unit): Unit = {
    new Timer("checker timer").scheduleAtFixedRate(new TimerTask {
      def run(): Unit = checker(new File(dir).length())
    }, 0, 1)
  }
}
