package knowbag.grails.pomodoro.pomodoro.vo

import groovy.time.TimeCategory
import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 10/11/14.
 */
class Pomodoro {

    private String description
    private Date startDate
    private Date endDate
    private User user
    private int lapseMinutes
    private boolean active

    static class Build {

        private String description = ""
        private User user
        private Date startDate = new Date()
        private int lapseMinutes = 15
        private boolean active = false

        Build(user) {
            this.user = user
        }

        Build withDescription(description) {
            this.description = description
            this
        }

        Build withStartDate(date) {
            this.startDate = date
            this
        }

        Build withLapseMinutes(lapse) {
            this.lapseMinutes = lapse
            this
        }

        Build withActive(active) {
            this.active = active
            this
        }

        Pomodoro build() {
            Date endDate
            use(TimeCategory) {
               endDate = this.startDate + this.lapseMinutes.minutes
            }
            new Pomodoro(description, startDate, endDate, user, lapseMinutes, active)
        }

    }

    Pomodoro(String description, Date startDate, Date endDate, User user, int lapse, active) {
        this.description = description
        this.startDate = startDate
        this.endDate = endDate
        this.user = user
        this.lapseMinutes = lapse
        this.active = active
    }

    String getDescription() {
        return description
    }

    Date getStartDate() {
        return startDate
    }

    Date getEndDate() {
        return endDate
    }

    User getUser() {
        return user
    }

    int getLapseMinutes() {
        return lapseMinutes
    }

    boolean isActive() {
        return active
    }
}
