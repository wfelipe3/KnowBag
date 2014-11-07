package knowbag.grails.pomodoro

import grails.converters.JSON
import knowbag.grails.pomodoro.user.UserManager
import knowbag.grails.pomodoro.user.exception.UserAlreadyExistsException
import knowbag.grails.pomodoro.user.exception.UserNotFoundException
import knowbag.grails.pomodoro.user.vo.User

class UserController {

    UserService userService

    def createUser() {
        def user = buildUser(request.JSON)
        try {
            UserManager.save(user, userService)
            render(contentType: "application/json") {
                saved = true
            }
        } catch (UserAlreadyExistsException ex) {
            render(contentType: "application/json") {
                saved = false
            }
        }
    }

    def getUserByName() {
        try {
            render UserManager.findByName(params.name, userService) as JSON
        } catch (UserNotFoundException ex) {
            render(contentType: "application/json") {
                errorMessage = "User ${params.name} not found"
            }
        }
    }

    private User buildUser(user) {
        new User.Builder(user.name).withEmail(user.email).build()
    }
}
