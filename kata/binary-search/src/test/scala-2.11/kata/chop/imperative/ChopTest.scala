package kata.chop.imperative

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/11/15.
 */
class ChopTest extends FlatSpec with Matchers {

  def chop2(list: List[Int], value: Int): Int = {
    println(s"list: $list value: $value")
    if (list.isEmpty) throw new NoSuchElementException
    else {
      val n: Int = list.length / 2
      val middle: Int = list(n)
      if (middle == value) n
      else if (value < middle) chop2(list.take(n), value)
      else {
        val (take, drop) = list.span(_ <= middle)
        chop2(drop, value) + take.length
      }
    }
  }

  "empty list" should "return -1" in {
    a[NoSuchElementException] shouldBe thrownBy(chop2(List(), 10))
    a[NoSuchElementException] shouldBe thrownBy(chop2(List(1), 10))
    chop2(List(1), 1) should be(0)
    a[NoSuchElementException] shouldBe thrownBy(chop2(List(1, 2), 10))
    chop2(List(1, 2), 1) should be(0)
    chop2(List(1, 2), 2) should be(1)
    a[NoSuchElementException] shouldBe thrownBy(chop2(List(1, 2, 3), 10))
    chop2(List(1, 2, 3), 1) should be(0)
    chop2(List(1, 2, 3), 2) should be(1)
    chop2(List(1, 2, 3), 3) should be(2)
    a[NoSuchElementException] shouldBe thrownBy(chop2(List(1,3,5,7), 8))
    chop2(List(1,3,5,7), 7) should be(3)
    chop2(List(11,20,55,109, 250, 1000, 2001, 2224, 9999), 1000) should be(5)
    chop2(List(11,20,55,109, 250, 1000, 2001, 2224, 9999), 20) should be(1)
  }

}
