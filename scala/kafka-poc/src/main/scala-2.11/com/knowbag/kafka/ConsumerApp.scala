package com.knowbag.kafka

import java.util
import java.util.Properties

import com.cj.kafka.rx.{Record, RxConsumer}
import scala.concurrent.duration._

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}

/**
  * Created by dev-williame on 9/28/16.
  */
object ConsumerApp extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("group.id", "test")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("session.timeout.ms", "30000")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  val consumer = new KafkaConsumer[String, String](props)

  val topics = new util.ArrayList[String]()
  topics.add("TutorialTopic")
  consumer.subscribe(topics)

  val conn = new RxConsumer("localhost:2181", "group")
  conn.getRecordStream("TutorialTopic")
    .map(deserializeRecord)
    .take(42 seconds)
    .foreach(println)

  //  println("before start")
  //
  //  while (true) {
  //    val consumerRecord = consumer.poll(100).asScala.toList
  //    consumerRecord.foreach(deserialize)
  //  }

  conn.shutdown()

  def deserialize: (ConsumerRecord[String, String]) => Unit =
    r => println(s"offset=${r.offset()} key=${r.key()} value=${r.value()}")

  def deserializeRecord: (Record[Array[Byte], Array[Byte]]) => Unit =
    r => println(s"offset=${r.offset} key=${r.key} value=${new String(r.value)}")
}
