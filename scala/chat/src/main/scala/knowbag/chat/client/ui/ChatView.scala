package knowbag.chat.client.ui

import knowbag.chat.client.connectivity.MessageSubject
import knowbag.chat.client.domain.Message

/**
 * Created by feliperojas on 4/27/15.
 */
class ChatView extends (Message => Unit) {

  MessageSubject.listen(this)

  override def apply(message: Message): Unit = println(message.line)
}
