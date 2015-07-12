package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class Filter[I, O](inPipe: Pipe[I], outPipe: Pipe[O]) extends ThreadRunner {


  override def run(): Unit = transformBetween(inPipe, outPipe)

  def transformBetween(pipe: Pipe[I], pipe1: Pipe[O]): Unit
}
