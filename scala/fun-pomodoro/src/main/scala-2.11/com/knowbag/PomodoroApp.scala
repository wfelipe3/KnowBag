package com.knowbag

import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch

import com.knowbag.kafka.Producer

import scala.concurrent.duration._

/**
  * Created by feliperojas on 10/17/16.
  */
object PomodoroApp extends App {

  val countDown = new CountDownLatch(1)
  val meta = PomoMeta(workTime = 5 second, restTime = 1 second)
  val now = () => LocalDateTime.now()

  val producer = Producer.createProducer


  PomodoroObservable.observable(Pomodoro.start(meta, "felipe")(now))(meta)(now)
    .take(10)
    .subscribe(
      onNext = { p =>
        Producer.sendMessage(p.toString, "pomodoro", producer)
        println(p)
      },
      onError = e => e.printStackTrace(),
      onCompleted = () => countDown.countDown()
    )

  countDown.await()
}
