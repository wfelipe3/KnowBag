package knowbag.chat.client.poc

import java.io.PrintWriter
import java.net.Socket

import scala.io.BufferedSource

/**
 * Created by feliperojas on 17/04/15.
 */
class SocketConnection(host: String, port: Int) extends ChatConnection {

  var socket: Socket = null
  var in: BufferedSource = null
  var out: PrintWriter = null

  override def start(): Unit = {
    socket = new Socket(host, port)
    in = new BufferedSource(socket.getInputStream)
    out = new PrintWriter(socket.getOutputStream, true)
  }


  override def get(): String = {
    val lines = in.getLines()
    lines.next()
  }

  override def send(message: String): Unit = {
    out.println(message)
  }
}
