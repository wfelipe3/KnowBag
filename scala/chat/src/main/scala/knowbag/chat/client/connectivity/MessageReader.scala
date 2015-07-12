package knowbag.chat.client.connectivity

import java.net.Socket

import knowbag.chat.client.domain.Message

import scala.io.BufferedSource

/**
 * Created by feliperojas on 4/27/15.
 */
class MessageReader(socket: Socket) {

  private val in = new BufferedSource(socket.getInputStream)

  def start() = {
    new Thread(new Runnable {
      override def run(): Unit = {
        while (true) {
          val iterator = in.getLines()
          val next: String = iterator.next()
          MessageSubject.notify(Message(next))
        }
      }
    }).start()
  }

}
