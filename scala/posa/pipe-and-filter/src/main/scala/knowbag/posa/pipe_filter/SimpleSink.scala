package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class SimpleSink[T](pipe: Pipe[T]) extends Sink[T](pipe){

  def handle(value: T): Unit

  override def takeFrom(pipe: Pipe[T]): Unit = {
    var value = pipe.next()
    while (value.isDefined) {
      handle(value.get)
      value = pipe.next()
    }
  }
}
