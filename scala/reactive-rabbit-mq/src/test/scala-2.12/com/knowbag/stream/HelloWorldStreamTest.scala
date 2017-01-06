package com.knowbag.stream

import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicReference

import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import com.rabbitmq.client.{AMQP, ConnectionFactory, DefaultConsumer, Envelope}
import org.scalatest.{FlatSpec, Matchers}
import rx.lang.scala.Observable
import rx.observables.AsyncOnSubscribe

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Created by dev-williame on 12/30/16.
  */
class HelloWorldStreamTest extends FlatSpec with Matchers {

  implicit val as = ActorSystem("helloWorld")
  implicit val mat = ActorMaterializer()

  "stream" should "work with vector" in {
    val source = Source(1 to 100)
    val factorial = source.scan(BigInt(1)) { (acc, next) =>
      acc * next
    }
    val result: Future[IOResult] = factorial
      .map(num => ByteString(s"$num\n"))
      .runWith(FileIO.toPath(Paths.get("factorials.txt")))

    result.map(_.wasSuccessful).foreach(println)
    factorial.runWith(lineSink("factorial2.txt"))
  }

  def lineSink(filename: String): Sink[BigInt, Future[IOResult]] =
    Flow[BigInt]
      .map(s => ByteString(s"$s\n"))
      .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  "rabbitmq" should "send value to queue" in {
    val factory = new ConnectionFactory
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare("test", false, false, false, null)
    channel.basicPublish("", "test", null, "hell World".getBytes)
    channel.close()
    connection.close()
  }

  "rabbitmq" should "get values from queue" in {
    val factory = new ConnectionFactory
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare("test", false, false, false, null)

    val observable = Observable.interval(100 millis).map { _ =>
      val values = new AtomicReference[String]
      channel.basicConsume("test", true, new DefaultConsumer(channel) {
        override def handleDelivery(tag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {
          val message = new String(body, "UTF-8")
          values.set(message)
        }
      })
      values.get
    }

    observable.publish(o => o).subscribe(onNext = v => println(v))
  }
}
