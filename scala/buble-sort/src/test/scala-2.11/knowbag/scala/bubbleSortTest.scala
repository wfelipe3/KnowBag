package knowbag.scala

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 10/1/15.
 */
class bubbleSortTest extends FlatSpec with Matchers {

  behavior of "bubble sort"

  it should "return empty list when empty list is sorted" in {
    bubbleSortAndAssert(unsorted = Nil, sorted = Nil)
  }

  it should "return same list when list is size one" in {
    bubbleSortAndAssert(unsorted = 1 :: Nil, sorted = 1 :: Nil)
    bubbleSortAndAssert(unsorted = 36 :: Nil, sorted = 36 :: Nil)
    bubbleSortAndAssert(unsorted = 76 :: Nil, sorted = 76 :: Nil)
  }

  it should "return sorted values when list is size two" in {
    bubbleSortAndAssert(unsorted = 56 :: 15 :: Nil, sorted = 15 :: 56 :: Nil)
    bubbleSortAndAssert(unsorted = 29 :: 85 :: Nil, sorted = 29 :: 85 :: Nil)
  }

  it should "return sorted values when list is size three" in {
    bubbleSortAndAssert(unsorted = 12 :: 10 :: 52 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
    bubbleSortAndAssert(unsorted = 12 :: 52 :: 10 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
    bubbleSortAndAssert(unsorted = 52 :: 10 :: 12 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
    bubbleSortAndAssert(unsorted = 52 :: 12 :: 10 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
    bubbleSortAndAssert(unsorted = 10 :: 52 :: 12 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
    bubbleSortAndAssert(unsorted = 10 :: 12 :: 52 :: Nil, sorted = 10 :: 12 :: 52 :: Nil)
  }

  it should "return sorted values for list with n values" in {
    bubbleSortAndAssert(unsorted = 10 :: 7 :: 98 :: 11 :: 87 :: 52 :: Nil, sorted = 7 :: 10 :: 11 :: 52 :: 87 ::98 :: Nil)
  }

  def bubbleSortAndAssert(unsorted: List[Int], sorted: List[Int]): Unit = {
    BubbleSort.sort(unsorted) should be(sorted)
  }
}
