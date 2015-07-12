package knowbag.chat.client

import knowbag.chat.client.poc.ChatConnection

/**
 * Created by feliperojas on 16/04/15.
 */
class GoodConnection extends ChatConnection{
  var messages: List[String] = List()
  var receivedMessages: List[String] = List()

  def receivedMessage(strings: String*) = strings.foreach(value => {
    receivedMessages = value :: receivedMessages
    receivedMessages = receivedMessages.reverse
  })

  override def start(): Unit = ()

  override def send(message: String): Unit = messages = message :: messages

  override def get(): String = {
    val head = receivedMessages.head
    receivedMessages = receivedMessages.tail
    head
  }
}
