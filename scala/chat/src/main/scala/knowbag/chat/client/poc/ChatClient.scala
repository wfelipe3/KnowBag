package knowbag.chat.client.poc

/**
 * Created by feliperojas on 16/04/15.
 */

class ChatClient(connection: ChatConnection) {
  startChatClient

  def sendMessage(message: String) = connection.send(message)

  def getMessage: String = connection.get()

  private def startChatClient: Unit = {
    try {
      connection.start()
    } catch {
      handleException()
    }
  }

  private def handleException(): PartialFunction[Any, Nothing] = {
    case th: Throwable => throw new ServerNotFoundException
  }
}

object ChatClient {
  def apply(connection: ChatConnection) = new ChatClient(connection)
}
