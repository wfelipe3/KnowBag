package knowbag.frp

/**
 * Created by feliperojas on 5/25/15.
 */
object NoSignal extends Signal[Nothing](???) {
  override def computeValue() = ()
}
