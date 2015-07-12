package knowbag.spray.helloworld

import spray.json.DefaultJsonProtocol

/**
 * Created by feliperojas on 7/5/15.
 */
object MyJsonProtocol extends DefaultJsonProtocol {

  implicit val personFormat = jsonFormat3(Person)
}

case class Person(name: String, firstName: String, age: Long) {}