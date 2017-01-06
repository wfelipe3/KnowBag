package com.knowbag.freemonad.interpreters

import com.knowbag.freemonad.model.FreeMonadModel._

import scalaz._

/**
  * Created by dev-williame on 1/4/17.
  */
object OptionMockInterpreter extends (Request ~> Option) {

  override def apply[A](in: Request[A]): Option[A] = in match {
    case Request(service) =>
      service match {
        case GetTweets(userId) =>
          println(s"Getting tweets for user $userId")
          None
        case GetUserName(userId) =>
          println(s"Getting user name for user $userId")
          userId match {
            case 1 => Some("Agnes")
            case 2 => Some("Brian")
            case _ => Some("Anonymous")
          }
        case GetUserId(userName) =>
          println(s"Getting user name for user $userName")
          userName match {
            case 1 => Some(1)
            case 2 => Some(2)
            case _ => Some(-1)
          }
        case GetUserPhoto(userId) =>
          println(s"Getting user photo for user $userId")
          userId match {
            case 1 => Some(":-)")
            case 2 => Some(":-D")
            case _ => Some(":-|")
          }
      }
  }
}
