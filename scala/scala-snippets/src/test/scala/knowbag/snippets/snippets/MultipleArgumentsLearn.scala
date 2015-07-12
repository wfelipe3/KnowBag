package knowbag.snippets.snippets

import knowbag.snippets.{PointDrawer, Point}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/04/15.
 */
class MultipleArgumentsLearn extends FlatSpec with Matchers {

  "A method can have multiple arguments list and arguments".should("be passed for every arg") in {
    PointDrawer.draw(Point()) {
      (x, y) => s"x=$x y=$y"} should be("x=0 y=0"
    )
    PointDrawer.draw(Point(x = 1, y = 2)) {
      (x, y) => s"x=$x y=$y"
    } should be("x=1 y=2")
  }

}
