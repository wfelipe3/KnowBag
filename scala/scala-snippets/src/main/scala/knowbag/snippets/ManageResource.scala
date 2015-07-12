package knowbag.snippets

import scala.util.control.NonFatal

/**
 * Created by feliperojas on 8/04/15.
 */
object ManageResource {

  def apply[R <: {def close():Unit}, T](resource: => R)(f: R => T) = {
    var source: Option[R] = None
    try {
      source = Some(resource)
      f(source.get)
    } catch {
      case NonFatal(ex) => -1
    } finally {
      for (s <- source) s.close()
    }
  }
}
