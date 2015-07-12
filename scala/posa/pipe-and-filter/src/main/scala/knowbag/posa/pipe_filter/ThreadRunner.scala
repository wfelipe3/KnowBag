package knowbag.posa.pipe_filter

/**
 * Created by feliperojas on 22/04/15.
 */
abstract class ThreadRunner extends Runnable {

  var isStarted = false

  def start() = {
    if (!isStarted) {
      isStarted = true
      val t = new Thread(this)
      t.start()
    }
  }

  def stop() = {
    isStarted = false
  }
}
