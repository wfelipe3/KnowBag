package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/04/15.
 */
class OptionLearn extends FlatSpec with Matchers {

  "Maps" should "return values with option instead the value and None instead null" in {
    val students = Map("felipe" -> 27, "juan" -> 25, "Maria" -> 26)
    students.get("felipe") should be(Some(27))
    students.get("juan").get should be(25)
    students.get("pepito") should be(None)
    students.get("Marina").getOrElse(-1) should be(-1)
    students.getOrElse("Marina", -1) should be(-1)
  }

}
