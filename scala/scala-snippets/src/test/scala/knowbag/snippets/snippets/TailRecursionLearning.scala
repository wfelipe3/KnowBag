package knowbag.snippets.snippets

import org.scalatest.{FlatSpec, Matchers}

import scala.util.control.TailCalls
import scala.util.control.TailCalls.TailRec

/**
 * Created by feliperojas on 6/16/15.
 */
class TailRecursionLearning extends FlatSpec with Matchers {

  behavior of "tail recursion"

  it should "make recursive calls with one stack" in {
    def factorial(n: Int): Int = {
      def factorial(n: Int, acum: Int): Int = {
        if (n == 0) acum
        else factorial(n - 1, n * acum)
      }
      factorial(n, 1);
    }

    factorial(2) should be(2)
    factorial(3) should be(6)
    factorial(4) should be(24)
  }

  it should "make trampoline tail calls when the recursion is in two different functions" in {
    def isEven(xs: List[Int]): TailRec[Boolean] = {
      if (xs.isEmpty) TailCalls.done(true)
      else isOdd(xs.tail)
    }

    def isOdd(xs: List[Int]): TailRec[Boolean] = {
      if (xs.isEmpty) TailCalls.done(false)
      else isEven(xs.tail)
    }

    val evenNumbers = for (i <- 1 to 5) yield isEven((1 to i).toList).result

    evenNumbers should be (List(false, true, false, true, false))
  }
}
