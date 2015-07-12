package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
class ExampleGenerator(pipe: Pipe[Int]) extends Generator[Int](pipe)  {

  override def generateInto(pipe: Pipe[Int]): Unit = {
    for {
      value <- 1 to 10
    } pipe.put(value)
  }
}
