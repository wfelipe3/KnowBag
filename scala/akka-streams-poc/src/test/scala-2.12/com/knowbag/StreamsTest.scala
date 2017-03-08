package com.knowbag

import java.nio.file.Paths

import akka.NotUsed
import akka.actor.ActorSystem
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.stream._
import akka.stream.scaladsl._
import akka.stream.stage.{GraphStage, GraphStageLogic, OutHandler}
import akka.util.ByteString
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import org.scalatest.{FreeSpec, Matchers}

import scala.concurrent.duration.{Duration, _}
import scala.concurrent.{Await, Future}
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dev-williame on 2/16/17.
  */
class StreamsTest extends FreeSpec with Matchers {

  implicit val system = ActorSystem("HelloWorld")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)
  val factorials: Source[BigInt, NotUsed] = source.scan(BigInt(1))(_ * _)

  "Source hello world" in {
    source.runForeach(println)
  }

  "write to file" in {
    val results: Future[IOResult] = factorials
      .map(num => ByteString(s"$num\n"))
      .runWith(FileIO.toPath(Paths.get("factorials.txt")))

    val value = Await.result(results, Duration.Inf)
    println(value)
  }

  "sink hello world" in {
    val flow = Flow[String].map(s => ByteString(s"$s\n"))
      .toMat(FileIO.toPath(Paths.get("factorials2.txt")))(Keep.right)

    factorials.map(_.toString).runWith(flow)
  }

  "time based processing" in {
    factorials.zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
      .throttle(2, 1 second, 1, ThrottleMode.shaping)
      .runForeach(println)

    Thread.sleep(10000000)
  }

  "kafka stream" in {
    val settings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val future = Consumer.plainSource(settings, Subscriptions.topics("test"))
      .map(c => Try(c.value().toInt))
      .throttle(1, 1 second, 1, ThrottleMode.shaping)
      .scan(0)((acc, value) => value.getOrElse(0) + acc)
      .runForeach(println)

    Await.ready(future, Duration.Inf)
  }

  "kafka producer" in {
    val producerSettings = ProducerSettings(system, new StringSerializer, new StringSerializer)
      .withBootstrapServers("localhost:9092")

    val done = Source(1 to 100000000)
      .map(_.toString)
      .map { elem => new ProducerRecord[String, String]("test", elem) }
      .runWith(Producer.plainSink(producerSettings))

    Await.ready(done, Duration.Inf)
  }

  "tcp binding" in {
    val connection = Tcp().bind("localhost", 2003).runForeach { connection =>
      println(s"New connection from: ${connection.remoteAddress}")

      val echo = Flow[ByteString]
        .via(Framing.delimiter(
          ByteString("\n"),
          maximumFrameLength = 2048,
          allowTruncation = false))
        .map(_.utf8String)
        .map(ByteString(_))

      connection.handleWith(echo)
    }

    Await.ready(connection, Duration.Inf)
  }

  "test" in {
    val (l1, l2) = scala.io.Source
      .fromFile("/Users/dev-williame/Downloads/jab2.csv")
      .getLines().toList
      .map(l => if (l.contains(";;")) l.replace(";;", "").trim else l)
      .map(_.split(";").map(_.trim))
      .foldLeft((List.empty[String], List.empty[String])) { (acc, value) =>
        val (v1, v2) = acc
        (v1 :+ value.head, v2 ++ value.tail)
      }

    println(l1.length)
    println(l2.length)
    //    l2.foreach(l => println(s"2:$l"))
    val x = l1 filterNot (l2 contains)
    println(x.length)
    //    x.foreach(println)
  }

  "test blocking message" in {
    Source.fromGraph(new NumbersSource).take(100).runForeach(println)
  }

  class NumbersSource extends GraphStage[SourceShape[Int]] {
    val out: Outlet[Int] = Outlet("NumbersSource")
    override val shape: SourceShape[Int] = SourceShape(out)

    override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
      new GraphStageLogic(shape) {
        // All state MUST be inside the GraphStageLogic,
        // never inside the enclosing GraphStage.
        // This state is safe to access and modify from all the
        // callbacks that are provided by GraphStageLogic and the
        // registered handlers.
        private var counter = 1

        setHandler(out, new OutHandler {
          override def onPull(): Unit = {
            push(out, counter)
            counter += 1
          }
        })
      }
  }
}
