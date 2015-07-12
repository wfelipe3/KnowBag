package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
class ExampleFilter(in: Pipe[Int], out: Pipe[String]) extends SimpleFilter[Int, String](in, out){
  override def transformOne(value: Int): String = {
    value.toString  + "This is a test"
  }
}
