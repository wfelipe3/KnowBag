package knowbag.drawer

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 27/03/15.
 */
class CanvasDrawerTest extends FlatSpec with Matchers {

  "A canvas with x < 0 and y < 0" should "throw illegal argumentException" in {
    a[IllegalArgumentException] should be thrownBy {
      drawCanvas(-1, -1)
    }
  }

  "A canvas with x = 0 and y = 0" should "throw illegal argument exception" in {
    a[IllegalArgumentException] should be thrownBy {
      drawCanvas(0, 0)
    }
  }

  "A canvas with x = 1 and y = 0" should "throw illegal argument exception" in {
    a[IllegalArgumentException] should be thrownBy {
      drawCanvas(1, 0)
    }
  }

  "A canvas with x = 0 and y = 1" should "throw illegal argument exception" in {
    a[IllegalArgumentException] should be thrownBy {
      drawCanvas(0, 1)
    }
  }

  "A canvas with x = 1 and y = 1" should "draw canvas with one row and one col" in {
    drawCanvas(1, 1) should be(" ")
  }

  "A canvas with x = 1 and y = 2" should "draw canvas with one row and two cols" in {
    drawCanvas(1, 2) should be("  ")
  }

  "A canvas with x = 1 and y = 3" should "draw canvas with one row and three cols" in {
    drawCanvas(1, 3) should be("   ")
  }

  "A canvas with x = 1 and y = 50" should "draw canvas with one row and 50 cols" in {
    drawCanvas(1, 50) should be(" " * 50)
  }

  "A canvas with x = 2 and y = 1" should "draw canvas with 2 rows and 1 col" in {
    drawCanvas(2, 1) should be(" \n ")
  }

  "A canvas with x = 3 and y = 1" should "draw canvas with 3 rows and 1 col" in {
    drawCanvas(3, 1) should be(" \n \n ")
  }

  def drawCanvas(x: Int, y: Int): String = {
    val canvas = Canvas(x, y)
    CanvasDrawer.draw(canvas)
  }
}
