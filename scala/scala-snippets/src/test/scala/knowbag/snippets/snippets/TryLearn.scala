package knowbag.snippets.snippets

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

/**
 * Created by feliperojas on 6/21/15.
 */
class TryLearn extends FlatSpec with Matchers {

  "try" should "get a value or recover" in {
    Try(Integer.parseInt("10")).getOrElse(-1) should be(10)
    Try(Integer.parseInt("nice")).getOrElse(-1) should be(-1)
  }

}
