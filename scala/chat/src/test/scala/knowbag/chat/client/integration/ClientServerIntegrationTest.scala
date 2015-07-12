package knowbag.chat.client.integration

import knowbag.chat.client.poc.{SocketConnection, ServerNotFoundException, ChatClient}
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 17/04/15
 */
class ClientServerIntegrationTest extends FlatSpec with Matchers {

  behavior of "Chat client"

  it should "throw ServerNotFoundException when a connection is created to an unreachable host" in {
    a[ServerNotFoundException] shouldBe thrownBy(ChatClient(new SocketConnection("localhost", 4000)))
  }

  it should "send a message to a server and get an echo" in {
    val client = ChatClient(new SocketConnection(host = "localhost", port = 3000))
    while (true) {
      client.sendMessage( """{"result":true}""".stripMargin)
      println(client.getMessage)
//      client.getMessage should be( """{"result":true}""".stripMargin)
//      client.sendMessage("end")
//      client.getMessage should be("good bye")
    }
  }

}
