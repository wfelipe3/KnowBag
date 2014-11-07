package knowbag.grails.pomodoro.user.exception

/**
 * Created by feliperojas on 5/11/14.
 */
class UserAlreadyExistsException extends RuntimeException {

    UserAlreadyExistsException(message) {
        super(message)
    }
}
