package com.knowbag

import java.time.LocalDateTime

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

/**
  * Created by feliperojas on 9/21/16.
  */
class PomodoroTest extends FlatSpec with Matchers {

  behavior of "Pomodoro"

  it should "Start Pomodoro in working state" in {
    val time: LocalDateTime = LocalDateTime.of(2016, 9, 12, 14, 0, 0)
    val pomodoro = Pomodoro.start(meta = PomoMeta(workTime = 3.second, restTime = 1.second), user = "felipe") _
    pomodoro(() => time) should be(Pomodoro("felipe", Work(time, time.plusSeconds(3))))
  }

  it should "stay in current state if actual end time is not reached" in {
    val now: LocalDateTime = LocalDateTime.now
    val actual = Pomodoro.transition(
      meta = PomoMeta(workTime = 3.second, restTime = 1.second),
      Pomodoro("felipe", Work(now, now.plusSeconds(3))))(() => LocalDateTime.now())
    actual should be(Pomodoro("felipe", Work(now, now.plusSeconds(3))))
  }

  it should "pass to rest state if actual end time was reached" in {
    val now: LocalDateTime = LocalDateTime.now
    val time: () => LocalDateTime = () => now.plusSeconds(4)
    val actual = Pomodoro.transition(
      meta = PomoMeta(workTime = 3.second, restTime = 1.second),
      Pomodoro("felipe", Work(now, now.plusSeconds(3)))
    )(time)
    actual should be(Pomodoro("felipe", Rest(time(), time().plusSeconds(1))))
  }

  it should "pass to work state if actual state is rest after the time was reached" in {
    val now: LocalDateTime = LocalDateTime.now
    val time: () => LocalDateTime = () => now.plusSeconds(1)
    val actual = Pomodoro.transition(
      meta = PomoMeta(workTime = 3.second, restTime = 1.second),
      Pomodoro("felipe", Rest(now, now.plusSeconds(1))))(time)
    actual should be(Pomodoro("felipe", Work(time(), time().plusSeconds(3))))
  }

}
