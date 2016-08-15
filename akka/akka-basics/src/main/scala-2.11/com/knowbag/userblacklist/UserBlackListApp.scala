package com.knowbag.userblacklist

import scala.language.postfixOps
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorSystem, Props, Actor}
import Storage._
import Recorder._
import Checker._

/**
  * Created by feliperojas on 7/24/16.
  */

case class User(username: String, email: String)

object Recorder {

  sealed trait RecorderMsg

  case class NewUser(user: User) extends RecorderMsg

  def props(checker: ActorRef, storage: ActorRef) =
    Props(new Recorder(checker, storage))
}

object Checker {

  sealed trait CheckerMsg

  case class CheckUser(user: User) extends CheckerMsg

  sealed trait CheckerResponse

  case class BlackUser(user: User) extends CheckerResponse

  case class WhiteUser(user: User) extends CheckerResponse

}

object Storage {

  sealed trait StorageMsg

  case class AddUser(user: User) extends StorageMsg

}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {

  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(5 seconds)

  override def receive: Receive = {
    case NewUser(user) =>
      checker ? CheckUser(user) map {
        case WhiteUser(user) =>
          storage ! AddUser(user)
        case BlackUser(user) =>
          println(s"User $user is in blacklist")
      }
  }
}

class Storage extends Actor {

  var users = List.empty[User]

  override def receive: Receive = {
    case AddUser(user) =>
      println(s"Storage: $user added")
      users = user :: users
  }
}

class Checker extends Actor {
  val blackList = List(
    User("felipe", "wf@gmail.com")
  )

  def receive = {
    case CheckUser(user) if blackList.contains(user) =>
      println(s"Checker: $user in the blacklist")
      sender() ! BlackUser(user)
    case CheckUser(user) =>
      println(s"Checker: $user in the whitelist")
      sender() ! WhiteUser(user)
  }
}

object UserBlackListApp extends App {
  // Create the 'talk-to-actor' actor system
  val system = ActorSystem("talk-to-actor")

  // Create the 'checker' actor
  val checker = system.actorOf(Props[Checker], "checker")

  // Create the 'storage' actor
  val storage = system.actorOf(Props[Storage], "storage")

  // Create the 'recorder' actor
  val recorder = system.actorOf(Recorder.props(checker, storage), "recorder")

  //send NewUser Message to Recorder
  recorder ! Recorder.NewUser(User("Jon", "jon@packt.com"))
  recorder ! Recorder.NewUser(User("felipe", "wf@gmail.com"))

  Thread.sleep(100)

  //shutdown system
  system.terminate()

}
