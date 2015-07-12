package knowbag.chat.client.main

import java.net.Socket

import knowbag.chat.client.connectivity.{MessageSerializer, SocketConnectionManager, MessageReader}
import knowbag.chat.client.domain.{Message, ChatClient}
import knowbag.chat.client.ui.{ChatView, ChatController}

/**
 * Created by feliperojas on 4/27/15.
 */
object Main extends App {

  val socket = new Socket("localhost", 3000)

  val connectionManager = new SocketConnectionManager(socket, new MessageSerializer[String] {
    override def serialize(message: Message): String = message.line
  })

  val client = new ChatClient(connectionManager)
  val controller = new ChatController(client)

  val view = new ChatView
  val messageReader = new MessageReader(socket)
  messageReader.start()
}
