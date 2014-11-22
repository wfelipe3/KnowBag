package knowbag.grails.pomodoro.user

import knowbag.grails.pomodoro.user.client.UserFind
import knowbag.grails.pomodoro.user.client.UserSave
import knowbag.grails.pomodoro.user.exception.UserAlreadyExistsException
import knowbag.grails.pomodoro.user.exception.UserNotFoundException
import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 5/11/14.
 */
class UserManager implements UserSave, UserFind {

    private def storer;

    UserManager(storer) {
        this.storer = storer
    }

    @Override
    void save(User user) {
        throwExceptionIfUserAlreadyExists(user)
        saveUser(user)
    }

    @Override
    User findByName(String name) {
        Optional.ofNullable(findUser(name)).orElseThrow({
            new UserNotFoundException(name)
        })
    }

    @Override
    List<User> findAll() {
        storer.findAll()
    }

    private void throwExceptionIfUserAlreadyExists(user) {
        Optional.ofNullable(findUser(user.name)).ifPresent({
            throw new UserAlreadyExistsException(user.name)
        })
    }

    private User findUser(name) {
        storer.findByName(name)
    }


    private void saveUser(user) {
        storer.save(user)
    }
}
