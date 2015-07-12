package knowbag.model.pomodoro

import java.util.{Timer, TimerTask}

import knowbag.infraestructure.scheduler.PomodoroScheduler
import knowbag.model.pomodoro.service.DefaultPomodoroService
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 4/30/15.
 */
class PomodoroSchedulerTest extends FlatSpec with Matchers {

  behavior of "pomodoro scheduler"

  it should "start a work phase for a given user" in {

    val subject = new PomodoroSubject
    subject.addObserver(new PomodoroScheduler(
      workTask = pomodoro => {
        pomodoro.phase.state should be(Phases.work)
      },
      restTask = {
        pomodoro => ()
      })
    )

    new DefaultPomodoroService(subject).startPomodoroWith(user = User("zombienator"))

    sleep(1000)
  }

  it should "start a rest phase after a work phase" in {
    val subject = new PomodoroSubject

    subject.addObserver(new PomodoroScheduler(
      workTask = pomodoro => {
        println(pomodoro)
        new DefaultPomodoroService(subject).endPhase(pomodoro)
      },
      restTask = pomodoro => {
        println(pomodoro)
        pomodoro.phase.state should be(Phases.rest)
        new DefaultPomodoroService(subject).endPhase(pomodoro)
      })
    )

    new DefaultPomodoroService(subject).startPomodoroWith(user = User("zombienator"))

    sleep(30000)
  }

  def sleep(time: Long): Unit = {
    Thread.sleep(time)
  }
}
