package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 8/04/15.
 */
class IfLearn extends FlatSpec with Matchers {

  "if are expressions that mean that they" should "return values like ternary operator in java" in {
    val pf: PartialFunction[Any, Boolean] = {
      case c: String => true
      case _ => false
    }
    val value: String = if (pf("value")) "String" else "not String"
    value should be("String")
  }

}
