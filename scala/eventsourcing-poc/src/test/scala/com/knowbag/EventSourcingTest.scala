package com.knowbag

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by williame on 6/28/17.
  */
class EventSourcingTest extends FreeSpec with Matchers {

  import PersonEvents._

  "test create user" in {
    PersonCommands.createUser() should be(PersonCreated())
  }

  "test update user" in {
    PersonCommands.updateUser(name = Some("felipe")) should be(PersonUpdated(name = Some("felipe"), None))
    PersonCommands.updateUser(age = Some(20)) should be(PersonUpdated(name = None, Some(20)))
  }

  "test aggregate user" in {
    aggregate(Person(), PersonUpdated(Some("felipe")), PersonUpdated(age = Some(20))) should be(Person("felipe", 20))
  }

  def aggregate(person: Person, personEvent: PersonEvent*): Person = {
    personEvent.foldRight(person) { (e, p) =>
      e match {
        case PersonCreated(n, a) =>
          val p1 = if (n.isDefined) p.copy(name = n.get) else p
          if (a.isDefined) p1.copy(age = a.get) else p1
        case PersonUpdated(n, a) =>
          val p1 = if (n.isDefined) p.copy(name = n.get) else p
          if (a.isDefined) p1.copy(age = a.get) else p1
      }
    }
  }

  case class Person(name: String = "", age: Int = 0)

  object PersonCommands {

    import PersonEvents._

    def createUser(): PersonCreated = PersonCreated()
    def updateUser(name: Option[String] = None, age: Option[Int] = None) = PersonUpdated(name, age)
  }

  object PersonEvents {

    trait PersonEvent

    case class PersonCreated(name: Option[String] = None, age: Option[Int] = None) extends PersonEvent

    case class PersonUpdated(name: Option[String] = None, age: Option[Int] = None) extends PersonEvent

    case class PersonDeleted(name: Option[String] = None) extends PersonEvent

  }

}
