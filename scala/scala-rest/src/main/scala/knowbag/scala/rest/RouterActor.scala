package knowbag.scala.rest

import akka.actor.Actor.Receive
import akka.actor.{ActorRefFactory, Actor}
import spray.routing.HttpService

/**
 * Created by feliperojas on 2/04/15.
 */
class RouterActor extends Actor with HttpService {

  override def receive: Receive = runRoute(aSimpleRoute ~ anotherRoute)

  override implicit def actorRefFactory: ActorRefFactory = context

  def test(message: String): Unit  = println(message)
}
