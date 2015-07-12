package knowbag.chat.client.ui

import java.util.Scanner

import knowbag.chat.client.domain.{Message, ChatClient}

/**
 * Created by feliperojas on 4/27/15.
 */
class ChatController(client: ChatClient) {

  val scanner = new Scanner(System.in)

  new Thread(new Runnable {
    override def run(): Unit = {
     while(true) {
       val line = scanner.nextLine()
       client.send(Message(line))
     }
    }
  }).start()

}
