package knowbag.chat.client.poc

/**
 * Created by feliperojas on 16/04/15.
 */
trait ChatConnection {
  def start()
  def send(message: String)
  def get(): String
}
