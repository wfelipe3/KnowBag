package knowbag.grails.pomodoro.pomodoro

import knowbag.grails.pomodoro.pomodoro.exception.PomodoroAlreadyRunningException
import knowbag.grails.pomodoro.pomodoro.exception.PomodoroException
import knowbag.grails.pomodoro.pomodoro.vo.Pomodoro
import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 10/11/14.
 */
class PomodoroManager {

    private def storer

    PomodoroManager(storer) {
        this.storer = storer
    }

    void start(Pomodoro pomodoro) {
        Optional.ofNullable(pomodoro.user).map({ user ->
            startIfNotActive(pomodoro)
            return user
        }).orElseThrow({
            new PomodoroException("The user is null")
        })
    }

    private void startIfNotActive(pomodoro) {
        throwExceptionIfHasActive(pomodoro)
        save(pomodoro)
    }

    private void save(Pomodoro pomodoro) {
        storer.save(pomodoro)
    }

    private void throwExceptionIfHasActive(Pomodoro pomodoro) {
        Pomodoro active = getActivePomodoro(pomodoro.user)
        if (active != null) {
            throw new PomodoroAlreadyRunningException("")
        }
    }

    private Pomodoro getActivePomodoro(User user) {
        List<Pomodoro> pomodoros = storer.findByUser(user)
        Pomodoro active = pomodoros.find {
            it.isActive()
        }
        active
    }
}
