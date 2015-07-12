package knowbag.model.poc.pomodoro

/**
 * Created by feliperojas on 4/28/15.
 */
trait PomodoroRepositoryPoc {
  def find(user: UserPoc): PomodoroPoc
}
