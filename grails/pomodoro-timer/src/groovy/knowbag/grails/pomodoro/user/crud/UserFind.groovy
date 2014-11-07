package knowbag.grails.pomodoro.user.crud

import knowbag.grails.pomodoro.user.vo.User
import knowbag.grails.pomodoro.user.exception.UserNotFoundException

/**
 * Created by feliperojas on 5/11/14.
 */
class UserFind {

    private def storer

    UserFind(storer) {
        this.storer = storer
    }

    User findByName(name) {
        Optional.ofNullable(findUser(name)).orElseThrow({
            new UserNotFoundException(name)
        })
    }

    List<User> findAll() {
        storer.findAll()
    }

    private User findUser(name) {
        storer.findByName(name)
    }

}
