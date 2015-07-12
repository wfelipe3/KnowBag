package knowbag.scala.functional

import java.util.Date

/**
 * Created by feliperojas on 31/01/15.
 */
class Person(val lastName: String) {

  var name: String = ""
  private var privateAge = 0
  val date = new Date

  def this(lastName: String, name: String) {
    this(lastName)
    this.name = name
  }

  def age = privateAge
  def age_=(age: Int) = privateAge = age

  def incrementAge(): Unit = {
    privateAge = privateAge + 1
  }

  def getFullName = s"$name $lastName"
}
