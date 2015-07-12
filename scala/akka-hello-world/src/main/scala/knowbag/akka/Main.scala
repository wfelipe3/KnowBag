package knowbag.akka

import akka.actor.{Props, ActorSystem}
import akka.util.Timeout
import akka.io.IO
import spray.can.Http

import scala.concurrent.duration._

/**
 * Created by feliperojas on 4/29/15.
 */
object Main extends App {

  implicit val system = ActorSystem("smartjava")
  val service = system.actorOf(props = Props[ServiceActor], "rest-service")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)

}
