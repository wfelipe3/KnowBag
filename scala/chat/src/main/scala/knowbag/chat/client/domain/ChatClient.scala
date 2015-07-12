package knowbag.chat.client.domain

import knowbag.chat.client.connectivity.ConnectionManager

/**
 * Created by feliperojas on 4/27/15.
 */
class ChatClient(connectionManager: ConnectionManager) {

  def send(message: Message): Unit = connectionManager.send(message)

}
