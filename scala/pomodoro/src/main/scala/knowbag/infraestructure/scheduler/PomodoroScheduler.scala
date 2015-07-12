package knowbag.infraestructure.scheduler

import java.util.{Timer, TimerTask}

import knowbag.model.pomodoro.{Pomodoro, RestPhase, WorkPhase}

/**
 * Created by feliperojas on 5/1/15.
 */
class PomodoroScheduler(workTask: Pomodoro => Unit,
                        restTask: Pomodoro => Unit,
                        config: SchedulerConfig = DefaultSchedulerConfig) extends (Pomodoro => Unit) {

  def apply(pomodoro: Pomodoro): Unit = {
    pomodoro.phase match {
      case phase: WorkPhase =>
        scheduleTask(pomodoro, getDelayTimeFor("work")) {
          workTask
        }
      case phase: RestPhase =>
        scheduleTask(pomodoro, getDelayTimeFor("rest")) {
          restTask
        }
    }
  }

  private def getDelayTimeFor(rest: String): Long = {
    config.getProperty[Int](rest)
  }

  private def scheduleTask(pomodoro: Pomodoro, delay: Long)(task: Pomodoro => Unit): Unit = {
    new Timer().schedule(new TimerTask {
      def run(): Unit = {
        task(pomodoro)
      }
    }, delay)
  }
}
