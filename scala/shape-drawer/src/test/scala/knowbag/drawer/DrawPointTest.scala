package knowbag.drawer

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 28/03/15.
 */
class DrawPointTest extends FlatSpec with Matchers {

  "A canvas with x = 1 and y = 1 and point(0,0)" should "create a point" in {
    CanvasDrawer.draw(Canvas(1, 1).addShape(Point(0, 0))) should be("-")
  }
}
