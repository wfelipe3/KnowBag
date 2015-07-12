package knowbag.model.pomodoro.service

import knowbag.model.pomodoro._

/**
 * Created by feliperojas on 4/30/15.
 */
class DefaultPomodoroService(subject: PomodoroSubject = new PomodoroSubject(List())) extends PomodoroService {

  self =>

  override def startPomodoroWith(user: User): Pomodoro = Pomodoro(PhaseFactory.newWorkPhase, user, subject)

  override def endPhase(pomodoro: Pomodoro): Pomodoro = pomodoro match {
    case Pomodoro(phase: RestPhase, _, _) => pomodoro.copy(phase = PhaseFactory.newWorkPhase)
    case Pomodoro(phase: WorkPhase, _, _) => pomodoro.copy(phase = PhaseFactory.newRestPhase)
  }
}
