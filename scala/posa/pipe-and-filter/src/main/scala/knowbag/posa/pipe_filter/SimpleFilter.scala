package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class SimpleFilter[I, O](inPipe: Pipe[I], outPipe: Pipe[O]) extends Filter[I, O](inPipe, outPipe){

  override def transformBetween(in: Pipe[I], out: Pipe[O]): Unit = {
    var inValue = in.next()
    while (inValue.isDefined) {
      val outValue = transformOne(inValue.get)
      out.put(outValue)
      inValue = in.next()
    }
  }

  def transformOne(value: I): O
}
