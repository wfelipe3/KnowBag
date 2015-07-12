package knowbag.model.pomodoro.service

import knowbag.model.pomodoro.{PomodoroSubject, Pomodoro, User}

/**
 * Created by feliperojas on 4/30/15.
 */
trait PomodoroService  {

  def startPomodoroWith(user: User): Pomodoro
  def endPhase(pomodoro: Pomodoro): Pomodoro
}
