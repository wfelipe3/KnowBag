package knowbag.chat.client.connectivity

import knowbag.chat.client.domain.Message

/**
 * Created by feliperojas on 4/27/15.
 */
trait MessageSerializer[T] {

  def serialize(message: Message): T
}
