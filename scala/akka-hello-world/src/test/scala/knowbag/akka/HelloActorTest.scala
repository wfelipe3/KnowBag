package knowbag.akka

import akka.actor.{Props, ActorSystem}
import org.scalatest.{WordSpec, Matchers, FlatSpec}

/**
 * Created by feliperojas on 4/29/15.
 */
class HelloActorTest extends WordSpec with Matchers {

  "An Actor" when {
    "a message is sent" should {
      "print hello world" in {
        val system = ActorSystem("HelloSystem")
        val helloActor = system.actorOf(props = Props[HelloActor], name = "helloactor")
        helloActor ! "hello"
        helloActor ! "something else"
      }
    }
  }
}
