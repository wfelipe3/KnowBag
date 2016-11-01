package knowbag.scalaz.day1

import org.scalatest.{FlatSpec, Matchers}
import scalaz._
import Scalaz._

/**
  * Created by dev-williame on 11/1/16.
  */
class Equal extends FlatSpec with Matchers {

  "Eq" should "be used for types that support equality" in {
    1 =/= 1 should be(false)
//    1 === 1 should be(true)
  }
}
