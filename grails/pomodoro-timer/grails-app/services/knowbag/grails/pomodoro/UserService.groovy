package knowbag.grails.pomodoro

import grails.transaction.Transactional
import knowbag.grails.pomodoro.user.vo.User

@Transactional
class UserService {

    void save(User user) {
        new UserDomain(name: user.name, email: user.email).save()
    }

    User findByName(name) {
        def userDomain = UserDomain.findByName(name)
        Optional.ofNullable(userDomain).map({
            new User.Builder(userDomain.name).withEmail(userDomain.email).build()
        }).orElse(null)
    }
}
