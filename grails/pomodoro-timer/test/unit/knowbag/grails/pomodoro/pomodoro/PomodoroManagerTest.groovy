package knowbag.grails.pomodoro.pomodoro

import groovy.time.TimeCategory
import knowbag.grails.pomodoro.pomodoro.exception.PomodoroAlreadyRunningException
import knowbag.grails.pomodoro.pomodoro.exception.PomodoroException
import knowbag.grails.pomodoro.pomodoro.vo.Pomodoro
import knowbag.grails.pomodoro.user.vo.User
import org.junit.Assert
import org.junit.Test

/**
 * Created by feliperojas on 10/11/14.
 */
class PomodoroManagerTest {

    public static final NULL_USER = null

    @Test(expected = PomodoroException)
    void givenStartPomodoroWithNullUser_ThenThrowPomodoroException() {
        def pomodoro = createPomodoroWith(NULL_USER)
        startPomodoro(pomodoro, createDataStorer())
    }

    @Test
    void givenExistingUserNameToStartPomodoro_ThenStartPomodoro() {
        def dataStorer = createDataStorer()
        def user = createUser()
        def pomodoro = createPomodoroWith(user)
        startPomodoro(pomodoro, dataStorer)
        assertPomodoro(dataStorer.entity, user)
    }

    @Test(expected = PomodoroAlreadyRunningException)
    void givenStartPomodoro_whenPomodoroWasAlreadyStartWithInRange_ThenThrowPomodoroAllReadyRunningException() {
        def dataStorer = createDataStorer()
        def user = createUser()
        def pomodoro = createPomodoroWith(user)
        startPomodoro(pomodoro, dataStorer)
        startPomodoro(pomodoro, dataStorer)
    }

    private def createDataStorer() {
        new PomodoroSpyDataStorer()
    }

    private startPomodoro(user, PomodoroSpyDataStorer storer) {
        new PomodoroManager(storer).start(user)
    }

    private Pomodoro createPomodoroWith(User user) {
        new Pomodoro.Build(user).withActive(true).build()
    }

    private User createUser() {
        new User.Builder("name")
                .withEmail("wfelpe3@gmail.com")
                .build()
    }

    private void assertPomodoro(entity, User user) {
        Assert.assertEquals(user, entity.user)
        Assert.assertEquals(entity.lapseMinutes, getMinutesDiference(entity.startDate, entity.endDate))
    }

    private int getMinutesDiference(Date startDate, Date endDate) {
        TimeCategory.minus(endDate, startDate).minutes
    }
}
