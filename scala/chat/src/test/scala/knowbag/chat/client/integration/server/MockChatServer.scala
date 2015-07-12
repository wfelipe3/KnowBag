package knowbag.chat.client.integration.server

import java.io.PrintWriter
import java.net.{Socket, ServerSocket}
import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.BufferedSource

/**
 * Created by feliperojas on 17/04/15
 */
object MockChatServer extends App {

  val serverSocket = new ServerSocket(3000)
  val counter = new AtomicInteger()
  while (true) {
    val socket = serverSocket.accept()
    val lines = readLines(socket)
    new Thread {
      while(true) {
        val line = lines.next()
        println(s"$line ${counter.incrementAndGet()}")
        1 to 1000 foreach(value => echoBack(socket, echo = line))
      }
    }.start()
  }

  def echoBack(socket: Socket, echo: String): Unit = {
    val writer = new PrintWriter(socket.getOutputStream, true)
    writer.println(echo)
  }

  def readLines(socket: Socket): Iterator[String] = {
    val in = new BufferedSource(socket.getInputStream)
    in.getLines()
  }
}
