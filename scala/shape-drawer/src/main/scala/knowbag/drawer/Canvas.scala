package knowbag.drawer

/**
 * Created by feliperojas on 28/03/15.
 */
class Canvas(val x: Int, val y: Int, val shapes: List[Shape]) {

  def addShape(shape: Shape): Canvas = {
    validate(shape)
    Canvas(x, y, shape :: shapes)
  }

  private def validate(shape: Shape): Unit = shape match {
    case Line(x, y) => if (x == 1 && y == 1) throw new ShapeDoesNotFitException
    case _ =>
  }
}

object Canvas {
  def apply(x: Int, y: Int, shapes: List[Shape] = List()): Canvas = {
    validateAllGreaterThanZero(x, y)
    new Canvas(x, y, shapes)
  }

  private def validateAllGreaterThanZero(values: Int*): Unit = {
    values.foreach(value => if (value <= 0) throw new IllegalArgumentException)
  }
}
