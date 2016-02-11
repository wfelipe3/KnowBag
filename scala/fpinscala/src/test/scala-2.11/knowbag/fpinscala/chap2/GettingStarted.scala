package knowbag.fpinscala.chap2

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 10/24/15.
 */
class GettingStarted extends FlatSpec with Matchers {

  "exercise 2.1" should "implement fibonacci" in {
    def fib(n: Int): Int = {
      if (n == 0) 0
      else if (n == 1) 1
      else fib(n - 2) + fib(n - 1)
    }

    def fibIter(n: Int): Int = {
      def loop(n: Int, prev: Int, actual: Int): Int = {
        if (n == 0) prev
        else loop(n - 1, actual, prev + actual)
      }

      loop(n, 0, 1)
    }

    fib(0) should be(0)
    fib(1) should be(1)
    fib(2) should be(1)
    fib(3) should be(2)
    fib(4) should be(3)
    fib(5) should be(5)

    fibIter(0) should be(0)
    fibIter(1) should be(1)
    fibIter(2) should be(1)
    fibIter(3) should be(2)
    fibIter(4) should be(3)
    fibIter(5) should be(5)
  }

  "exercise 2.2" should "implement is sorted function" in {
    def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
      def loop(a: A, as: Array[A]): Boolean = {
        if (!ordered(a, as.head)) false
        else if (as.length > 1) loop(as.head, as.tail)
        else ordered(a, as.head)
      }

      if (as.length > 1) loop(as.head, as.tail)
      else true
    }

    val ordered: (Int, Int) => Boolean = (a, b) => a < b

    isSorted[Int](Array(), ordered) should be(true)
    isSorted(Array(1), ordered) should be(true)
    isSorted(Array(1, 2), ordered) should be(true)
    isSorted(Array(2, 1), ordered) should be(false)
    isSorted(Array(1, 2, 3), ordered) should be(true)
    isSorted(Array(1, 3, 2), ordered) should be(false)
    isSorted(Array(2, 1, 3), ordered) should be(false)
    isSorted(Array(2, 3, 1), ordered) should be(false)
    isSorted(Array(3, 1, 2), ordered) should be(false)
    isSorted(Array(3, 2, 1), ordered) should be(false)
  }

  "exercise 2.3" should "Implement curry" in {
    def partial[A, B, C](a: A, f:(A, B) => C) =
      (b: B) => f(a, b)

    def curry[A, B, C](f: (A, B) => C): A => B => C =
      (a:A) => (b: B) => f(a ,b)

    val sum: (Int, Int) => Int = (a, b) => a + b
    val curriedSum: Int => Int => Int = curry(sum)
    val sum4: Int => Int = curriedSum(4)
    sum4(2) should be(6)
    sum4(6) should be(10)
  }

  "exercise 2.4" should "Implement uncurry" in {
    def uncurry[A, B, C](f: A => B => C): (A, B) => C =
      (a: A, b: B) => f(a)(b)

    val sum = (a:Int) => (b: Int) => a + b
    val uncurriedSum = uncurry(sum)
    uncurriedSum(3, 4) should be(7)
  }

  "exercise 2.5" should "Implement compose" in {
    def compose[A, B, C](f: B => C, g: A => B): A => C =
      (a: A) => f(g(a))

    val intToString = (a: Int) => a.toString
    val stringToFloat = (b: String) => b.toFloat

    val intToFloat = compose(stringToFloat, intToString)
    intToFloat(20) should be(20.0f)
  }

}
