package com.knowbag.free

import org.scalatest.{FlatSpec, Matchers}

import scalaz.{Free, ~>, Id, Coyoneda}
import scalaz.std.list._
import scalaz.syntax.traverse._

/**
  * Created by dev-williame on 11/9/16.
  */
class FreeMonadTest extends FlatSpec with Matchers {

  "" should "" in {
  }

  object Orchestration {
    type UserId = Int
    type UserName = String
    type UserPhoto = String

    type Requestable[A] = Coyoneda[Request, A]

    final case class Tweet(userId: UserId, msg: String)
    final case class User(id: UserId, name: UserName, photo: UserPhoto)

    sealed trait Service[A]
    final case class GetTweets(userId: UserId) extends Service[List[Tweet]]
    final case class GetUserName(userId: UserId) extends Service[UserName]
    final case class GetUserPhoto(userId: UserId) extends Service[UserPhoto]

    sealed trait Request[A]
    final case class Pure[A](a: A) extends Request[A]
    final case class Fetch[A](service: Service[A]) extends Request[A]

    object Request {
      def pure[A](a: A): Free[Requestable, A] =
        Free.liftFC(Pure(a): Request[A])

      def fetch[A](service: Service[A]): Free[Requestable, A] =
        Free.liftFC(Fetch(service): Request[A])
    }
  }

  import Orchestration._

  object ToyInterpreter extends (Request ~> Id.Id) {

    import Id._

    override def apply[A](fa: Request[A]): Id[A] = fa match {
      case Pure(a) => a
      case Fetch(service) =>
        service match {
          case GetTweets(userId) =>
            println(s"Getting tweets for user $userId")
            List(Tweet(1, "Hi"), Tweet(2, "Hi"), Tweet(1, "Bye"))
          case GetUserName(userId) =>
            println(s"Getting username for user $userId")
            userId match {
              case 1 => "Agnes"
              case 2 => "Brian"
              case _ => "Anonymous"
            }
          case GetUserPhoto(userId) =>
            println(s"Getting user photo for user $userId")
            userId match {
              case 1 => ":-)"
              case 2 => ":-D"
              case _ => ":-|"
            }
        }
    }
  }
}

