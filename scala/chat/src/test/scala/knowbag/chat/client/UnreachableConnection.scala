package knowbag.chat.client

import knowbag.chat.client.poc.ChatConnection

/**
 * Created by feliperojas on 16/04/15.
 */
class UnreachableConnection(host: String, port: Int) extends ChatConnection {
  def start() = throw new RuntimeException

  override def send(message: String): Unit = ???

  override def get(): String = ???
}
object UnreachableConnection {
  def apply(host: String, port: Int = 3000) = new UnreachableConnection(host, port)
}
