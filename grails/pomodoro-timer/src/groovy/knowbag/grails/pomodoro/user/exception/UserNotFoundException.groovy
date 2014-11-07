package knowbag.grails.pomodoro.user.exception

/**
 * Created by feliperojas on 5/11/14.
 */
class UserNotFoundException extends RuntimeException{

    UserNotFoundException(message) {
        super(message)
    }
}
