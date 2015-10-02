package dojos.bz.math.interpreter

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 9/8/15.
 */
class PrefixProcessorTest extends FlatSpec with Matchers {

  behavior of "prefix processor"

  it should "return the sum of several operands prefixed with + operator" in {
    evalPrefixExpAndAssert("+ 2 8", 10)
    evalPrefixExpAndAssert("+ 3 9", 12)
    evalPrefixExpAndAssert("+ 76 87 98", 76 + 87 + 98)
  }

  it should "return the subtraction of several operands prefixed with - operator" in {
    evalPrefixExpAndAssert("- 2 8", -6)
    evalPrefixExpAndAssert("- 100 65 32", 100 - 65 - 32)
  }

  it should "return multiplication of several operands prefixed with * operator" in {
    evalPrefixExpAndAssert(exp = "* 0 1", expected = 0 * 1)
    evalPrefixExpAndAssert(exp = "* 10", expected = 10)
    evalPrefixExpAndAssert(exp = "* 12 23 63", expected = 12 * 23 * 63)
  }

  it should "return division of several operands prefixed with / operator" in {
    evalPrefixExpAndAssert(exp = "/ 10 5", expected = 2)
    evalPrefixExpAndAssert(exp = "/ 100 2 5", expected = 100 / 2 / 5)
  }

  it should "return sum for expression with parenthesis" in {
    evalPrefixExpAndAssert(exp = "(+ 10 8)", expected = 10 + 8)
  }

  def evalPrefixExpAndAssert(exp: String, expected: Int): Unit = {
    PrefixProcessor.eval(exp) should be(expected)
  }
}
