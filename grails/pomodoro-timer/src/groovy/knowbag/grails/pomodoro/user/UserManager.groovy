package knowbag.grails.pomodoro.user

import knowbag.grails.pomodoro.user.crud.UserFind
import knowbag.grails.pomodoro.user.crud.UserSave
import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 5/11/14.
 */
final class UserManager {

    private UserManager() {}

    static void save(user, storer) {
        UserSave saver = new UserSave(user, storer)
        saver.save()
    }

    static User findByName(String name, def storer) {
        UserFind finder = new UserFind(storer)
        finder.findByName(name)
    }

    static List<User> findAll(storer) {
        UserFind finder = new UserFind(storer)
        finder.findAll()
    }
}
