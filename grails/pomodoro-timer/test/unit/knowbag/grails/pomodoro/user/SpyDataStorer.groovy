package knowbag.grails.pomodoro.user

/**
 * Created by feliperojas on 5/11/14.
 */
class SpyDataStorer {

    def entity

    void save(entity) {
        this.entity = entity
    }

    def findByName(entity) {
        return this.entity
    }
}
