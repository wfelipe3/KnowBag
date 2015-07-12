package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 15/04/15.
 */
class MixinLearn extends FlatSpec with Matchers {

  behavior of "mixins and traits"

  it should "be possible to add logic to a trait that extends other in order to mixin logic for a specific service" in {
    val service1 = new ImportantService("one")

    val service2 = new ImportantService("dos") with ConsoleLogging {
      override def work(i: Int) = {
        info(s"Starting work: i = $i")
        val result = super.work(i)
        info(s"Ending work: i = $i, result = $result")
        result
      }
    }

    printService(service1)
    printService(service2)
    printService(new LoggedImportantService("three"))
  }

  def printService(service: ImportantService): Unit = {
    1 to 3 foreach (i => println(s"Result: ${service.work(i)}"))
  }

  class ImportantService(name: String) {
    def work(i: Int) = {
      println(s"important: doing important work $i")
      i
    }
  }

  class LoggedImportantService(name: String) extends ImportantService(name) with ConsoleLogging {
  }

  trait Logging {
    def info(message: String)
    def warning(message: String)
    def error(message: String)
  }
  
  trait ConsoleLogging extends Logging {
    def info(message: String) = println(s"INFO: $message")
    def warning(message: String) = println(s"WARNING: $message")
    def error(message: String) = println(s"ERROR: $message")
  }
}
