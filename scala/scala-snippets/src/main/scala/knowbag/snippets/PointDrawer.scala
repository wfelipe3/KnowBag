package knowbag.snippets

/**
 * Created by feliperojas on 6/04/15.
 */
object PointDrawer {

  def draw(p: Point)(f: (Int, Int) => String) = f(p.x, p.y)
}
