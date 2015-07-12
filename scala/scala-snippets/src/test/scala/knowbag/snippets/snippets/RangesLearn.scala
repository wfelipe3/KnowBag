package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 5/04/15.
 */
class RangesLearn extends FlatSpec with Matchers {

  "When a range from 1 to 10 is created" should "be iterated by all numbers" in {
    val values = new StringBuilder
    1.to(10).foreach(values.append)
    values.toString should be("12345678910")
  }

  "When a range from 1 until 10 is created" should "be iterated from 1 to 9" in {
    val values = 1.until(10).mkString(" ")
    values should be("1 2 3 4 5 6 7 8 9")
  }

  "When a range from 1 to 10 by 2 is created" should "be iterated from 1 to 10 by 2" in {
    val values = (1 to 10 by 2).mkString(" ")
    values should be("1 3 5 7 9")
  }

  "When a range from a to h is crated" should "be iterated letter by letter" in {
    ('a' to 'h').mkString(" ") should be("a b c d e f g h")
  }
}
