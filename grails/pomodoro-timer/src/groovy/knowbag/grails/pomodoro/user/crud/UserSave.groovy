package knowbag.grails.pomodoro.user.crud

import knowbag.grails.pomodoro.user.vo.User
import knowbag.grails.pomodoro.user.exception.UserAlreadyExistsException

/**
 * Created by feliperojas on 5/11/14.
 */
class UserSave {

    private User user
    private def storer

    UserSave(User user, storer) {
        this.user = user
        this.storer = storer
    }

    void save() {
        throwExceptionIfUserAlreadyExists()
        saveUser()
    }

    private void throwExceptionIfUserAlreadyExists() {
        Optional.ofNullable(findUser()).ifPresent({
            throw new UserAlreadyExistsException(user.name)
        })
    }

    private User findUser() {
        storer.findByName(user.name)
    }


    private void saveUser() {
        storer.save(user)
    }
}
