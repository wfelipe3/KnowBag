package knowbag.kata.karatechop

import org.scalatest.FlatSpec

/**
 * Created by feliperojas on 16/03/15.
 */
class KarateChopTest extends FlatSpec {

  "A null list" should "throw NoSuchElementException" in {
    assertNoSuchElementException(5, null)
  }

  "A empty list" should "throw NoSuchElementException" in {
    assertNoSuchElementException(5, List())
  }

  "A list with values [5]" should "throw NoSuchElementException when value 6 is used" in {
    assertNoSuchElementException(value = 6, list = List(5))
  }

  it should "be 0 when value 5 is used" in {
    assertChop(t = 5, values = List(5), expected = 0)
  }

  "A list with values [2,3]" should "be 1 when value 3 is used" in {
    assertChop(t = 3, values = List(2,3), expected = 1)
  }

  private def assertNoSuchElementException(value: Int = 5, list: List[Int]): Unit = {
    intercept[NoSuchElementException] {
      KarateChop.chop(value, list)
    }
  }

  def assertChop(t: Int = 5, values: List[Int], expected: Int): Unit = {
    assert(KarateChop.chop(t, values) === expected)
  }
}
