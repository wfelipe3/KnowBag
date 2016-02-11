package knowbag.pomo

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 10/23/15.
 */
class PomodoroServiceTest extends FlatSpec with Matchers {

  behavior of "pomodoro service"

  it should "start work for a given user" in {
    val shouldBeWorkTime: (Int) => Unit = _ should be(25)
    val pomodoro = pomodoroWithAssertionScheduler(shouldBeWorkTime).work(Pomodoro(user = "felipe", state = PomodoroState.start))
    pomodoro.state should be(PomodoroState.work)
  }

  it should "start rest phase for  a given user" in {
    val shouldBeRestTime: (Int) => Unit = _ should be(5)
    val pomodoro = pomodoroWithAssertionScheduler(shouldBeRestTime).rest(Pomodoro(user = "felipe", state = PomodoroState.work))
    pomodoro.state should be(PomodoroState.rest)
  }

  private def pomodoroWithAssertionScheduler(assertion: (Int) => Unit): PomodoroService = {
    PomodoroService(mockSchedulerWithAssertion(assertion))
  }

  private def mockSchedulerWithAssertion(assertion: Int => Unit) = new Scheduler {
    override def startTask(time: Int): Unit = assertion(time)
  }
}
