package knowbag.drawer

import knowbag.drawer.Canvas

import scala.collection.mutable.ListBuffer

/**
 * Created by feliperojas on 27/03/15.
 */
object CanvasDrawer {

  def addShape(line: Line): Canvas = throw new ShapeDoesNotFitException

  def draw(canvas: Canvas): String = {
    var frame = List.tabulate(canvas.x)(i => List.tabulate(canvas.y)(j => " "))
    canvas.shapes.foreach(shape => {
      shape match {
        case Point(x, y) => frame = frame.updated(x, frame(x).updated(y, "-"))
      }
    })
    frame.map(v => v.mkString("")).mkString("\n")
  }
}
