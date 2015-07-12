package knowbag.model.poc.pomodoro

/**
 * Created by feliperojas on 4/28/15.
 */
object PomodoroManagerPoc {

  def startPomodoro(user: UserPoc, eventManager: EventManagerPoc): PomodoroPoc = new PomodoroPoc(user, phase = PhasePoc.work, eventManager)

  def endPhase(pomodoro: PomodoroPoc, eventManager: EventManagerPoc) = pomodoro.phase match {
    case PhasePoc.work => new PomodoroPoc(user = pomodoro.user, phase = PhasePoc.rest, eventManager)
    case PhasePoc.rest => new PomodoroPoc(user = pomodoro.user, phase = PhasePoc.work, eventManager)
  }

  def find(user: UserPoc, repository: PomodoroRepositoryPoc): PomodoroPoc = repository.find(user)
}
