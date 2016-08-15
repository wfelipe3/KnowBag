package com.knowbag.helloworld

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by feliperojas on 7/24/16.
  */
case class WhoToGreet(who: String)

class Greeter extends Actor {
  override def receive: Receive = {
    case WhoToGreet(who) => println(s"hello $who")
  }
}

object HelloWorldAkkaApp extends App {

  val system = ActorSystem("Hello-World-Akka")

  val greeter = system.actorOf(Props[Greeter], "Greeter")

  greeter ! WhoToGreet("felipe")
}
