package knowbag.chat.server

/**
 * Created by feliperojas on 5/4/15.
 */
object ServerMain extends App {

  val server = new ChatServer(3000)
  server.startServer()

}
