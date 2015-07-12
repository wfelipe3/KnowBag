package knowbag.chat.client.connectivity

import knowbag.chat.client.domain.Message

/**
 * Created by feliperojas on 4/27/15.
 */
object MessageSubject {

  var listeners: List[Message => Unit] = Nil

  def listen(listener: Message => Unit): Unit = {
    listeners ::= listener
  }

  def notify(message: Message): Unit = listeners.foreach(listener => listener(message))

}
