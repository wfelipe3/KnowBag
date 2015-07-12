package knowbag.akka

import akka.actor.Actor

/**
 * Created by feliperojas on 4/29/15.
 */
class HelloActor extends Actor {

  override def receive: Receive = {
    case "hello" => println("hello back")
    case _ => println("what")
  }
}
