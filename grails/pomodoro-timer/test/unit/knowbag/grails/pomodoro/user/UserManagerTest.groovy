package knowbag.grails.pomodoro.user

import knowbag.grails.pomodoro.user.exception.UserAlreadyExistsException
import knowbag.grails.pomodoro.user.exception.UserNotFoundException
import knowbag.grails.pomodoro.user.vo.User
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals

/**
 * Created by feliperojas on 5/11/14.
 */
class UserManagerTest {

    public static final String FELIPE = "felipe"

    private SpyDataStorer spyStore

    @Before
    void before() {
        spyStore = new SpyDataStorer()
    }

    @Test
    void givenUserToSave_thenSaveUserInDataStorage() {
        save(buildUser(FELIPE, "wfelipe3@gmail.com"))
        assertEqualName(FELIPE, spyStore.entity.name)
    }

    @Test(expected = UserAlreadyExistsException)
    void givenUserWithExistingNameToSave_thenThrowUserAlreadyExistException() {
        save(buildUser(FELIPE, "wfelipe3@gmail.com"))
        save(buildUser(FELIPE, "wfelipe3@gmail.com"))
    }

    @Test
    void givenFindExistingUserByName_thenReturnUser() {
        save(buildUser(FELIPE, "wfelipe3@gmail.com"))
        User user = find(FELIPE)
        assertEqualName(FELIPE, user.name)
    }

    @Test(expected = UserNotFoundException)
    void givenFindNonExistingUserByName_thenThrowUserNotFoundException() {
        find(FELIPE)
    }

    @Test
    void givenFindAllUsers_ThenReturnAllUsers() {
        addFindAllToSpy()
        List<User> users = findAll()
        assertEquals(3, users.size())
    }

    private List<User> findAll() {
        UserManager.findAll(spyStore)
    }

    private void addFindAllToSpy() {
        spyStore.getMetaClass().findAll = {
            [new User.Builder(FELIPE).build(), new User.Builder("juan").build(), new User.Builder("jose").build()]
        }
    }

    private User buildUser(String name, String email) {
        new User.Builder(name).withEmail(email).build()
    }

    private assertEqualName(String expectedName, String actualName) {
        assertEquals(expectedName, actualName)
    }

    private User find(String name) {
        UserManager.findByName(name, spyStore)
    }

    private save(name) {
        UserManager.save(name, spyStore)
    }

}
