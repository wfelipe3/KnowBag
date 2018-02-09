package knowbag.mail

import javax.mail.{Folder, Message, Session}

/**
  * Created by feliperojas on 2/8/18.
  */
object MailMain extends App {

  val props = System.getProperties
  props.setProperty("mail.store.protocol", "imaps")
  val session = Session.getDefaultInstance(props, null)
  val store = session.getStore("imaps")

  store.connect("imap.gmail.com", args(0), args(1))

  val inbox = store.getFolder("Inbox")
  inbox.open(Folder.READ_ONLY)

  inbox.search { msg =>
    println(msg.getFrom.map(_.toString).mkString(","))
    Option(msg.getSubject)
      .filter(s => s.toUpperCase().contains("BBVA"))
      .exists(_ => true)
  }
    .toStream
    .take(100)
    .map(message)
    .foreach(println)

  //  inbox.getMessages()
  //    .toStream
  //    .take(100)
  //    .map(message)
  //    .foreach(println)

  inbox.close(true)

  def message(m: Message) = {
    s"subject: {${m.getSubject}}"
  }

}
