package knowbag.model.pomodoro.repository

import knowbag.model.pomodoro.{Pomodoro, User}

/**
 * Created by feliperojas on 4/30/15.
 */
trait PomodoroRepository {

  def findBy(user: User): Pomodoro
}
