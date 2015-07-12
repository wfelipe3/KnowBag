package knowbag.chat.client.domain

/**
 * Created by feliperojas on 4/27/15.
 */
class Message(val line: String) {

}
object Message {
  def apply(line: String) = new Message(line)
}