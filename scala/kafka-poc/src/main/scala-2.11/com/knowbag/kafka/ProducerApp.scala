package com.knowbag.kafka

import java.time.LocalDateTime
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by dev-williame on 9/28/16.
  */
object ProducerApp extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  val topic = "TutorialTopic"

  for (i <- 10 to 50) {
    val record = new ProducerRecord(topic, "key", s"hello $i")
    producer.send(record)
  }

  val record = new ProducerRecord(topic, "key", s"the end ${LocalDateTime.now()}")
  producer.send(record)

  producer.close()
}
