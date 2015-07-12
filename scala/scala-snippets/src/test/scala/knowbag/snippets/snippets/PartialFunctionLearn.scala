package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

import scala.util.Try

/**
 * Created by feliperojas on 5/04/15.
 */
class PartialFunctionLearn extends FlatSpec with Matchers {

  "A partial function is defined with the case construct and" should "be created when cannot be defined for all posible inputs" in {
    isStringPF("message") should be("yes")
  }

  "A value different than string in partial function " should "return no" in {
    isStringPF(12) should be("no")
  }

  "Partial functions can be combined and" should "be evaluated as one or else" in {
    val pfString: PartialFunction[Any, String] = {case s: String => "String"}
    val pfInt: PartialFunction[Any, String] = {case i: Int => "Int"}
    val pfStringOrInt = pfString.orElse(pfInt)
    pfStringOrInt("value") should be("String")
    pfStringOrInt(2) should be("Int")
    an[MatchError] shouldBe thrownBy(pfStringOrInt(true))
  }

  "A partial function that matches for String " should "be defined for String value" in {
    val pfString: PartialFunction[Any, String] = {case s: String => "String"}
    pfString.isDefinedAt("value") should be(right = true)
    pfString.isDefinedAt(5) should be(right = false)
  }

  def isStringPF = {
    PartialFunction[Any, String] {
      case s: String => "yes"
      case _ => "no"
    }
  }
}
