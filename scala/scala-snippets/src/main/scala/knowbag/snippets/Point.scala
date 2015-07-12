package knowbag.snippets

/**
 * Created by feliperojas on 6/04/15.
 */
case class Point(x: Int = 0, y: Int = 0) {
  def move(x: Int, y: Int = 0) = Point(x + this.x, y + this.y)
}
