package com.knowbag

import java.time.LocalDateTime

import scala.concurrent.duration.Duration
import scalaz.Lens

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

  val pomodoroLens = Lens.lensu[Pomodoro, State](set = (p, s) => p.copy(state = s), get = _.state)

  private def nextState(meta: PomoMeta, pomodoro: Pomodoro, actualTime: LocalDateTime): Pomodoro = {
    pomodoro match {
      case Pomodoro(_, Work(_, _)) =>
        pomodoroLens.set(pomodoro, Rest(actualTime, actualTime.plusSeconds(meta.restTime.toSeconds)))
      case Pomodoro(_, Rest(_, _)) =>
        pomodoroLens.set(pomodoro, Work(actualTime, actualTime.plusSeconds(meta.workTime.toSeconds)))
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

