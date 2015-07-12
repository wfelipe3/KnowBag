package knowbag.chat.client.connectivity

import java.io.PrintWriter
import java.net.Socket

import knowbag.chat.client.domain.Message

/**
 * Created by feliperojas on 4/27/15.
 */
class SocketConnectionManager(socket: Socket, messageSerializer: MessageSerializer[String]) extends ConnectionManager {

  private val out: PrintWriter = new PrintWriter(socket.getOutputStream, true)

  def send(message: Message): Unit = out.println(messageSerializer.serialize(message))
}
