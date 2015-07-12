package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 5/04/15.
 */
class ExampleLearn extends FlatSpec with Matchers {

  "When an array of strings is upped" should "upper all values in string list" in {
    def upper(strings: String*): String = {
      strings.map(_.toUpperCase).mkString(" ")
    }
    upper("hello", "world") should be("HELLO WORLD")
  }
}
