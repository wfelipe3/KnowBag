package knowbag.posa.pipe_filter

import java.util

/**
 * Created by feliperojas on 22/04/15.
 */
class PipeImpl[T] extends Pipe[T] {

  val buffer: util.Queue[T] = new util.LinkedList[T]()
  var isOpenForWriting = true
  var hasReadLastObject = false

  override def put(t: T): Boolean = {
    if (!isOpenForWriting) {
      throw new RuntimeException("pipe is closed")
    }
    val wasAdded = buffer.add(t)
    this.synchronized {
      notify()
    }
    wasAdded
  }

  override def next(): Option[T] = {
    while(buffer.isEmpty)
      this.synchronized {
        wait()
      }

    val t = buffer.remove()

    if (t == null)
      None
    else
      Some(t)
  }

  override def closeForWriting(): Unit = ???
}
