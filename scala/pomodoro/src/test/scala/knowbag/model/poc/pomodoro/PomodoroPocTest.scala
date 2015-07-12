package knowbag.model.poc.pomodoro

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 4/28/15.
 */
class PomodoroPocTest extends FlatSpec with Matchers {

  behavior of "Pomodoro"

  it should "start a pomodoro in work phase" in {
    val pomodoro: PomodoroPoc = startPomodoro(user = UserPoc("wfelipe"))
    pomodoro.phase should be(PhasePoc.work)
  }

  it should "end work phase with rest phase" in {
    val pomodoro = startPomodoro(user = UserPoc("wfelipe"))
    val next = endPhase(pomodoro)
    next.phase should be(PhasePoc.rest)
  }

  it should "end rest phase with work phase" in {
    val restPomodoro = findPomodoro(phase = PhasePoc.rest, user = UserPoc("wfelipe"))
    val work = endPhase(restPomodoro)
    work.phase should be(PhasePoc.work)
  }

  it should "notify work event start" in {
    startPomodoro(user = UserPoc("wfelipe"), getMockEventManager(phase = PhasePoc.work))
  }

  it should "notify rest phase started" in {
    val pomodoro = findPomodoro(phase = PhasePoc.rest, UserPoc("wfelipe"), getMockEventManager(phase = PhasePoc.rest))
    endPhase(pomodoro)
  }

  private def getMockEventManager(phase: PhasePoc.Value): EventManagerPoc = {
    new EventManagerPoc {
      override def notifyEvent(pomodoro: PomodoroPoc): Unit = pomodoro.phase should be(phase)
    }
  }

  private def startPomodoro(user: UserPoc, eventManager: EventManagerPoc = getEmptyEventManager): PomodoroPoc = {
    PomodoroManagerPoc.startPomodoro(user = user, eventManager = eventManager)
  }

  private def findPomodoro(phase: PhasePoc.Value, user: UserPoc, eventManager: EventManagerPoc = getEmptyEventManager): PomodoroPoc = {
    PomodoroManagerPoc.find(user, new PomodoroRepositoryPoc {
      override def find(user: UserPoc): PomodoroPoc = new PomodoroPoc(user, phase, eventManager)
    })
  }

  def endPhase(pomodoro: PomodoroPoc): PomodoroPoc = {
    PomodoroManagerPoc.endPhase(pomodoro, getEmptyEventManager)
  }

  def getEmptyEventManager: EventManagerPoc with Object {def notifyEvent(pomodoro: PomodoroPoc): Unit} = {
    new EventManagerPoc {
      override def notifyEvent(pomodoro: PomodoroPoc): Unit = ()
    }
  }
}
