package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class Generator[T](pipe: Pipe[T]) extends ThreadRunner{

  def generateInto(pipe: Pipe[T]): Unit

  override def run(): Unit = {
    generateInto(pipe)
  }
}
