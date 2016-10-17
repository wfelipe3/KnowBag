package com.knowbag

import java.time.LocalDateTime

import scala.concurrent.duration.Duration

/**
  * Created by feliperojas on 10/9/16.
  */
object Pomodoro {

  def start(meta: PomoMeta, user: String)(f: () => LocalDateTime): Pomodoro = {
    val now = f()
    Pomodoro(user, Work(now, now.plusSeconds(meta.workTime.toSeconds)))
  }

  def transition(meta: PomoMeta, pomodoro: Pomodoro)(time: () => LocalDateTime): Pomodoro = {
    val actualTime: LocalDateTime = time()
    if (isAfterOrEqual(actualTime, pomodoro.state.end))
      nextState(meta, pomodoro, actualTime)
    else
      pomodoro
  }

  private def nextState(meta: PomoMeta, pomodoro: Pomodoro, actualTime: LocalDateTime): Pomodoro = {
    pomodoro match {
      case Pomodoro(_, Work(_, _)) =>
        pomodoro.copy(state = Rest(actualTime, actualTime.plusSeconds(meta.restTime.toSeconds)))
      case Pomodoro(_, Rest(_, _)) =>
        pomodoro.copy(state = Work(actualTime, actualTime.plusSeconds(meta.workTime.toSeconds)))
    }
  }

  private def isAfterOrEqual(actualTime: LocalDateTime, end: LocalDateTime): Boolean = {
    actualTime.isAfter(end) || actualTime.isEqual(end)
  }
}

case class Pomodoro(user: String, state: State)

sealed trait State {
  val start: LocalDateTime
  val end: LocalDateTime
}

case class Work(start: LocalDateTime, end: LocalDateTime) extends State

case class Rest(start: LocalDateTime, end: LocalDateTime) extends State

case class PomoMeta(workTime: Duration, restTime: Duration)

