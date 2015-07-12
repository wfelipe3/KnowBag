package knowbag.scala.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import spray.can.Http
import scala.concurrent.duration._

/**
 * Created by feliperojas on 2/04/15.
 */
object Boot extends App {

  implicit val system = ActorSystem("knowbag")
  val service = system.actorOf(Props[RouterActor], "sj-rest-service")
  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
