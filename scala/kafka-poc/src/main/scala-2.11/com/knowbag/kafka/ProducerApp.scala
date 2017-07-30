package com.knowbag.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import rx.lang.scala.Observable

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dev-williame on 9/28/16.
  */
object ProducerApp extends App {

  def createProducer: KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    new KafkaProducer[String, String](props)
  }

  def sendMessage(message: String, topic: String, producer: KafkaProducer[String, String]): Unit = {
    producer.send(new ProducerRecord(topic, "key", message))
  }


  val topic = "test"
  val producer = createProducer

//  (1 to 10).foreach { i =>
//    Future {
//      Observable.timer(initialDelay = 0 seconds, period = 1 millis)
//        .map { v =>
//          println(v, s"hello $i: $v")
//          v
//        }
//        .subscribe(l => sendMessage(s"hello $i: $l", topic, producer))
//    }
//  }

  sendMessage(
    """
      |{
      |   "timestamp": 12345,
      |   "topic": "test",
      |   "host": "http://localhost:8080",
      |   "scenario": "com.bizagi.simulations.TestSimulation",
      |   "setup": "atOnceUsers:10u,rampUsersPerSec:10u:20u:10m"
      |}
    """.stripMargin, topic, producer)

//  Thread.sleep(300000000)

  producer.close()
}
