package knowbag.snippets.snippets

import knowbag.snippets.Point
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/04/15.
 */
class MethodAndClassArgumentsLearn extends FlatSpec with Matchers {

  "A Class can have named arguments and" should "be created with those arguments specified in class creation" in {
    val point = Point(y = 2, x = 1)
    point.x should be (1)
    point.y should be (2)
  }

  "A class can have default argument values and" should "be created with those default values if not specified" in {
    val point = Point(y = 9)
    point.x should be (0)
    point.y should be (9)
  }

  "Methods can have default values an named arguments and" should "have the same behaviour as classes" in {
    val movedPoint = Point(y = 6).move(x = 4)
    movedPoint.x should be (4)
    movedPoint.y should be (6)
  }

  "A case class can be copied and" should "have the same values as the copied values but the new values specified" in {
    val point = Point(1, 3)
    point.copy(y = 7).y should be(7)
  }
}
