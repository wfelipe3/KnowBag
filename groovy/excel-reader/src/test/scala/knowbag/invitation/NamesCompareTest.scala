package knowbag.invitation

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 10/19/15.
 */
class NamesCompareTest extends FlatSpec with Matchers {

  behavior of "Names Compare"

  it should "return empty when both list are empty" in {
    compareNamesAndAssert(n1 = List(), n2 = List(), expected = Nil)
  }

  it should "return values from first list when second list is empty" in {
    compareNamesAndAssert(
      n1 = List("felipe rojas"),
      n2 = Nil,
      expected = List("felipe rojas")
    )
  }

  it should "not return values from first list when second list has the same name with different cases" in {
    compareNamesAndAssert(
      n1 = List("felipe rojas"),
      n2 = List("felipe ROJAS"),
      expected = Nil
    )
  }

  it should "not return values from fist list when second list has the same name splited with diffetent with space" in {
    compareNamesAndAssert(
      n1 = List("felipe   rojas"),
      n2 = List("felipe rojas"),
      expected = Nil
    )
  }

  ignore should "return values from second list when first list is empty" in {
    compareNamesAndAssert(
      n1 = List(),
      n2 = List("maria paula"),
      expected = List("maria paula")
    )
  }

  ignore should "return empty list when bot list are equal" in {
    compareNamesAndAssert(
      n1 = List("felipe rojas"),
      n2 = List("maria paula"),
      expected = List("felipe rojas", "maria paula")
    )
  }

  def compareNamesAndAssert(n1: List[String], n2: List[String], expected: List[String]): Unit = {
    def assert(names: Seq[String]): Unit = {
      names should be(expected)
    }
    assert(NamesCompare.compare(n1, n2))
  }
}
