package knowbag.drawer

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 27/03/15.
 */
class DrawLineTest extends FlatSpec with Matchers {

  "A canvas with x = 1 and y = 1 and line from p(0,0) to p(0,1)" should "throw exception" in {
    a[ShapeDoesNotFitException] should be thrownBy {
      Canvas(1, 1).addShape(Line(Point(0, 0), Point(1, 1)))
    }
  }

}
