package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
class ExampleSink(pipe: Pipe[String]) extends SimpleSink[String](pipe){
  override def handle(value: String): Unit = print(value)
}
