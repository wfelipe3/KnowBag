package com.knowbag.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import rx.lang.scala.Observable

import scala.concurrent.duration._

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

  val topic = "TutorialTopic"
  val producer = createProducer

  Observable.timer(initialDelay = 0 seconds, period = 15 millis)
    .map { v =>
      println(v)
      v
    }
    .subscribe(l => sendMessage(s"hello $l", topic, producer))

  Thread.sleep(300000000)

  producer.close()
}
