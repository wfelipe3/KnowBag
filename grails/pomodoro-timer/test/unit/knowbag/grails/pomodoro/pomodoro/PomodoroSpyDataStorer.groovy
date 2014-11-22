package knowbag.grails.pomodoro.pomodoro

import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 5/11/14.
 */
class PomodoroSpyDataStorer {

    def entity
    def List entities = []

    void save(entity) {
        entities << entity
        this.entity = entity
    }

    List findByUser(User user) {
        entities
    }
}
