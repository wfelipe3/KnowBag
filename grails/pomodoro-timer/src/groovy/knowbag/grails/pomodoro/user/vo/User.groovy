package knowbag.grails.pomodoro.user.vo

/**
 * Created by feliperojas on 5/11/14.
 */
class User {

    private String name
    private String email

    static class Builder {
        private String name
        private String email = ""

        Builder(name) {
            this.name = name
        }

        Builder withEmail(email) {
            this.email = email
            this
        }

        User build() {
            new User(this.name, this.email)
        }
    }

    private User(String name, String email) {
        this.name = name
        this.email = email
    }

    String getName() {
        name
    }

    String getEmail() {
        email
    }
}
