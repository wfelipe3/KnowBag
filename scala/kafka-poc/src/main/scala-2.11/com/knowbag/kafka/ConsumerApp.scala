package com.knowbag.kafka

import java.util
import java.util.Properties
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

import com.cj.kafka.rx.{Record, RxConsumer}

import scala.concurrent.duration._
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by dev-williame on 9/28/16.
  */
object ConsumerApp extends App {

  val countDown = new CountDownLatch(1)

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("group.id", "test")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("session.timeout.ms", "30000")
  props.put("auto.offset.reset", "earliest")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  val consumer = new KafkaConsumer[String, String](props)

  val topics = new util.ArrayList[String]()
  topics.add("TutorialTopic")
  topics.add("pomodoro")
  topics.add("test")
  topics.add("gatlingTest")

  consumer.subscribe(topics)

  val int = new AtomicInteger()
  Future {
    while (true) {
      val consumerRecord = consumer.poll(1000).asScala.toList
      consumerRecord.foreach(deserialize)
      val ko = consumerRecord
        .flatMap(c => c.value().split("\n"))
        .filter(_.contains("ko"))
        .map(_.replace(""""ko":""", ""))
        .map(_.trim.toInt)
        .sum
      if (ko > 0) println(ko + " =============================")
    }
  }

  val conn = new RxConsumer("localhost:2181", "group")
  conn.getRecordStream("pomodoro")
    .map(deserializeRecord)
    .take(100 seconds)
    .foreach(println)

  conn.shutdown()

  def deserialize: (ConsumerRecord[String, String]) => Unit =
    r => {
      println(s"offset=${r.offset()} key=${r.key()} value=${r.value()}")
    }

  def deserializeRecord: (Record[Array[Byte], Array[Byte]]) => Unit =
    r => println(s"offset=${r.offset} key=${r.key} value=${new String(r.value)}")

  countDown.await()
}
