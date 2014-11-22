package knowbag.grails.pomodoro.pomodoro.exception

/**
 * Created by feliperojas on 12/11/14.
 */
class PomodoroAlreadyRunningException extends RuntimeException {

    PomodoroAlreadyRunningException(message) {
        super(message)
    }
}
