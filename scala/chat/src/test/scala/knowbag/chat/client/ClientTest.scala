package knowbag.chat.client

import knowbag.chat.client.poc.{ServerNotFoundException, ChatConnection, ChatClient}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas
 */
class ClientTest extends FlatSpec with Matchers {

  behavior of "Chat client"

  it should "throw exception when a connection could not be stablished" in {
    a[ServerNotFoundException] should be thrownBy createChatClient(withUnreachableConnection)
  }

  it should "be created when the connection is stablished" in {
    createChatClient(withGoodConnection)
  }

  it should "be able to send one message to server" in {
    val goodConnection = withGoodConnection
    sendMessageWith(goodConnection, "echo please")
    goodConnection.messages should be(List("echo please"))
  }

  it should "be able to get message from a server" in {
    val goodConnection: GoodConnection = withGoodConnection
    goodConnection.receivedMessage("echoed")
    val client = createChatClient(goodConnection)
    client.getMessage should be("echoed")
  }

  it should "be able to get two messages from a server" in {
    val goodConnection: GoodConnection = withGoodConnection
    goodConnection.receivedMessage("echoed", "more message?")
    val client = createChatClient(goodConnection)
    client.getMessage should be("echoed")
    client.getMessage should be("more message?")
  }

  private def createChatClient(connection: ChatConnection): ChatClient = {
    ChatClient(connection)
  }

  private def withUnreachableConnection: UnreachableConnection = {
    val unreachableConnection = UnreachableConnection(host = "localhost", port = 3000)
    unreachableConnection
  }

  private def withGoodConnection: GoodConnection = {
    new GoodConnection
  }

  private def sendMessageWith(goodConnection: GoodConnection, message: String): Unit = {
    val client = createChatClient(goodConnection)
    client.sendMessage(message)
  }
}
