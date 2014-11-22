package knowbag.grails.pomodoro.user.client

import knowbag.grails.pomodoro.user.vo.User

/**
 * Created by feliperojas on 12/11/14.
 */
public interface UserFind {

    User findByName(String name)
    List<User> findAll()
}