package com.knowbag.freemonad.program

import com.knowbag.freemonad.interpreters.OptionMockInterpreter
import com.knowbag.freemonad.model.FreeMonadModel._

import scalaz.Scalaz._
import scalaz.{Free, _}

/**
  * Created by dev-williame on 1/4/17.
  */
object FreeMonadProgram {

  def fetch[A](service: Service[A]): Free[Request, A] =
    Free.liftF[Request, A](Request(service): Request[A])

  val theId: UserId = 1

  def getUser(id: UserId): Free[Request, User] =
    for {
      name <- fetch(GetUserName(id))
      photo <- fetch(GetUserPhoto(id))
    } yield User(id, name, photo)

  val free: Free[Request, List[(String, User)]] =
    for {
      tweets <- fetch(GetTweets(theId))
      result <- (tweets map { tweet: Tweet =>
        for {
          user <- getUser(tweet.userId)
        } yield tweet.msg -> user
      }).sequenceU
    } yield result

  def runWithOption: Option[List[(String, User)]] = {
    free.foldMap(OptionMockInterpreter)
  }
}
