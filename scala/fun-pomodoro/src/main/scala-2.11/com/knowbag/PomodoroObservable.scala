package com.knowbag

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

import rx.lang.scala.{Observable, Subscriber}

import scala.concurrent.duration._

/**
  * Created by feliperojas on 10/17/16.
  */
object PomodoroObservable {

  private def timer(p: Pomodoro, m: PomoMeta): Observable[Pomodoro] =
    Observable
      .timer(LocalDateTime.now().until(p.state.end, ChronoUnit.MILLIS) millis)
      .map(_ => p)

  def observable(p: Pomodoro)(meta: PomoMeta)(now: () => LocalDateTime): Observable[Pomodoro] = {
    def event(pomodoro: Pomodoro, s: Subscriber[Pomodoro]): Unit = {
      s.onNext(pomodoro)
      timer(pomodoro, meta)
        .subscribe(p => {
          event(Pomodoro.transition(meta, p)(now), s)
        }, e => s.onError(e))
    }

    Observable.apply { s =>
      event(p, s)
    }
  }

}
