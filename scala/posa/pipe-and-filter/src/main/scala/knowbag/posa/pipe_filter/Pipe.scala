package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
trait Pipe[T] {
  def put(t: T): Boolean
  def next(): Option[T]
  def closeForWriting(): Unit
}
