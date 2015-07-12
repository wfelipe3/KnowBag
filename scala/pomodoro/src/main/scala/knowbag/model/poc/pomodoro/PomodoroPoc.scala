package knowbag.model.poc.pomodoro

/**
 * Created by feliperojas on 4/28/15.
 */
class PomodoroPoc(val user: UserPoc, val phase: PhasePoc.Value, eventManager: EventManagerPoc) {
  eventManager.notifyEvent(this)
}
