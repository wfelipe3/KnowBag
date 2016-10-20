package com.knowbag.kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by feliperojas on 10/17/16.
  */
object Producer {

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

}
