package com.poc

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorRefFactory, ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import spray.http.MediaTypes._
import spray.routing.HttpService
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by dev-williame on 8/13/16.
  */
case class User(name: String, email: String)

object User {
  implicit val userFormat = jsonFormat2(User.apply)
}

object RestTestApp extends App {
  implicit val system = ActorSystem("perfDevOps")
  val service = system.actorOf(Props[RestActor])
  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = Integer.getInteger("port", 8080))
}

class RestActor extends Actor with HttpService {

  val blockingExecutionContext = {
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(2000))
  }

  import context.dispatcher

  def actorRefFactory: ActorRefFactory = context

  override def receive: Receive = runRoute(route)

  val route =
    path("test") {
      get {
        parameters('time.as[Int] ? 0) { time =>
          respondWithMediaType(`application/json`) {
            complete {
              Future {
                if (time > 0) Thread.sleep(time)
                User("felipe", "wfelipe3@gmail.com")
              }(blockingExecutionContext)
            }
          }
        }
      }
    }

}
