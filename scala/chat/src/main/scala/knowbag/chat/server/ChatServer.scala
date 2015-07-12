package knowbag.chat.server

import java.io.PrintWriter
import java.net.{Socket, ServerSocket}
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import scala.io.BufferedSource

/**
 * Created by feliperojas on 5/4/15.
 */
class ChatServer(port: Int) {

  val threadPool = Executors.newFixedThreadPool(10)
  val serverSocket = new ServerSocket(port)
  val atomicInt = new AtomicInteger()
  val running = true

  def startServer(): Unit = {
    runServer()
  }

  private def runServer(): Unit = {
    if (running)
      acceptSocketAndEchoBack()
    runServer()
  }

  private def acceptSocketAndEchoBack(): Unit = {
    val socket = serverSocket.accept()
    threadPool.execute(new Runnable {
      override def run(): Unit = {
        val lines = readLines(socket)
        echoNextLine(lines, socket)
      }
    })
  }

  private def echoNextLine(lines: Iterator[String], socket: Socket) {
    val line = lines.next()
    if (line equals "end")
      echoBack(socket, echo = "good bye")
    else {
      echoBack(socket, line)
      echoNextLine(lines, socket)
    }
  }

  private def readLines(socket: Socket): Iterator[String] = {
    val in = new BufferedSource(socket.getInputStream)
    in.getLines()
  }

  private def echoBack(socket: Socket, echo: String): Unit = {
    val writer = new PrintWriter(socket.getOutputStream, true)
    writer.println(s"$echo ${atomicInt.incrementAndGet()}")
  }
}
