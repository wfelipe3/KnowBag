package knowbag.pomo

/**
 * Created by feliperojas on 10/23/15.
 */
class PomodoroService(scheduler: Scheduler, workTime: Int, restTime: Int) {
  def rest(pomodoro: Pomodoro): Pomodoro = {
    scheduler.startTask(restTime)
    pomodoro.copy(state = PomodoroState.rest)
  }

  def work(pomodoro: Pomodoro): Pomodoro = {
    scheduler.startTask(workTime)
    pomodoro.copy(state = PomodoroState.work)
  }
}

object PomodoroService {
  def apply(scheduler: Scheduler) = new PomodoroService(scheduler = scheduler, workTime = 25, restTime = 5)
}
