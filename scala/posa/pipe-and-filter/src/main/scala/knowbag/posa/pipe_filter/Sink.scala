package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class Sink[T](pipe: Pipe[T]) extends ThreadRunner {

  override def run() {
    takeFrom(pipe)
  }

  def takeFrom(pipe: Pipe[T])
}
