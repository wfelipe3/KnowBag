package knowbag.model.pomodoro

/**
 * Created by feliperojas on 4/30/15.
 */
case class Pomodoro(phase: Phase, user: User, subject: PomodoroSubject) {
  subject.notify(this)
}
