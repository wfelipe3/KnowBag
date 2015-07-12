
package knowbag.model.pomodoro

import knowbag.model.pomodoro.service.DefaultPomodoroService
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 4/30/15.
 */
class PomodoroServiceTest extends FlatSpec with Matchers {

  val zombienator: String = "zombienator"

  behavior of "Pomodoro"

  it should "Start with a user in a work phase" in {
    var notified: Option[Pomodoro] = None
    val subject = new PomodoroSubject(List(pomodoro => notified = Some(pomodoro)))
    val pomodoro: Pomodoro = startPomodoro(user = getUser(zombienator), subject)
    pomodoro.user should be(getUser(zombienator))
    pomodoro.phase.state should be(Phases.work)
    notified.get.phase.state should be(Phases.work)
  }

  it should "move pomodoro phase from work to rest" in {
    var notified: Option[Pomodoro] = None
    val subject = new PomodoroSubject(List(pomodoro => notified = Some(pomodoro)))
    val pomodoroWithWorkPhase: Pomodoro = startPomodoro(user = getUser(zombienator), subject)
    val pomodoroWithRestPhase = endPhase(pomodoroWithWorkPhase, subject)
    pomodoroWithRestPhase.phase.state should be(Phases.rest)
    notified.get.phase.state should be(Phases.rest)
  }

  it should "move pomodoro phase from rest to work" in {
    var notified: Option[Pomodoro] = None
    val subject = new PomodoroSubject(List(pomodoro => notified = Some(pomodoro)))
    val pomodoroWithRestPhase = endPhase(startPomodoro(getUser(zombienator), subject))
    val pomodoroInWorkPhase = endPhase(pomodoroWithRestPhase, subject)
    pomodoroInWorkPhase.phase.state should be(Phases.work)
    notified.get.phase.state should be(Phases.work)
  }

  def endPhase(pomodoro: Pomodoro, subject: PomodoroSubject = new PomodoroSubject(List())): Pomodoro = {
    val service: DefaultPomodoroService = getPomodoroService(subject)
    service.endPhase(pomodoro)
  }

  def startPomodoro(user: User, subject: PomodoroSubject = new PomodoroSubject(List())): Pomodoro = {
    val service: DefaultPomodoroService = getPomodoroService(subject)
    service.startPomodoroWith(user = user)
  }

  def getPomodoroService(subject: PomodoroSubject): DefaultPomodoroService = {
    new DefaultPomodoroService(subject)
  }

  def getUser(name: String): User = {
    User(name)
  }
}
