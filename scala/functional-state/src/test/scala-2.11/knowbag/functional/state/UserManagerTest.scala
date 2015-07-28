package knowbag.functional.state

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/25/15.
 */
class UserManagerTest extends FlatSpec with Matchers {

  behavior of "UserManager"

  it should "get a value from a remote location and store it in cache" in {
    val (_, next) = UserManager.getUser("felipe").run(Map[String, User]())
    val (_, next2) = UserManager.getUser("felipe").run(next)
    val (_, next3) = UserManager.getUser("felipe").run(next2)
    println(next3)
  }

  object UserManager {

    type Cache = Map[String, User]

    def getUser(name: String): State[Cache, User] =
      for {
        ou <- checkCache(name)
        u <- ou
          .map(State.unit[Cache, User])
          .getOrElse(searchUser(name))
      } yield u

    def searchUser(name: String): State[Cache, User] = State { cache =>
      val user = callWebservice(name)
      (user, cache + (name -> user))
    }

    def callWebservice(name: String): User =
      if (name == "felipe")
        User("felipe", 27)
      else
        throw new NoSuchElementException

    def checkCache(name: String): State[Cache, Option[User]] = State { cache =>
      (cache.get(name), cache)
    }

  }

  case class User(name: String, cc: Int)

}
