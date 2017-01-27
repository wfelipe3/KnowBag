package com.bizagi.load

import java.util.concurrent.Executors

import net.liftweb.json.{DefaultFormats, _}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scalaj.http.Http


/**
  * Created by feliperojas on 11/13/16.
  */
object DistLoadApp extends App {

  implicit val formats = DefaultFormats
  val blockingExecutionContext = {
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(200))
  }

  val urls = args(0).split("@")

  val futures = urls.map(url => Future {
    val response = Http(url).postForm.asString
    parse(response.body).extract[Log]
  }(blockingExecutionContext))

  val logs = Await.result(Future.sequence(futures), Duration.Inf)
  val superLog = logs.foldRight(Log("")) { (l, acc) =>
    Log(s"${acc.log}\n${l.log}")
  }

  println(superLog)

}

case class Log(log: String)
