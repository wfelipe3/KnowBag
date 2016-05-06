package knowbag.fpinscala.chap2

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by feliperojas on 3/13/16.
  */
class GettingStarted_2 extends FlatSpec with Matchers {

  "exercise 2.1" should "implement fibonacci" in {
    def fib(n: Int): Int = {
      if (n == 0) 0
      else if (n == 1) 1
      else fib(n - 1) + fib(n - 2)
    }

    def fibI(n: Int): Int = {
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

    fibI(0) should be(0)
    fibI(1) should be(1)
    fibI(2) should be(1)
    fibI(3) should be(2)
    fibI(4) should be(3)
    fibI(5) should be(5)
  }

  "exercise 2.2" should "implement is sorted" in {
    def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
      if (as.isEmpty || as.length == 1) true
      else ordered(as.head, as.tail.head) && isSorted(as.tail, ordered)
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

  "exercise 2.3" should "implement currying" in {
    def curry[A, B, C](f: (A, B) => C): A => (B => C) =
      (a: A) => (b: B) => f(a, b)

    def partial[A, B, C](a: A, f: (A, B) => C): B => C =
      (b: B) => f(a, b)
  }

  "exercise 2.4" should "implement uncurry" in {
    def uncurry[A, B, C](f: A => B => C): (A, B) => C =
      (a: A, b: B) => f(a)(b)
  }

  "exercise 2.5" should "implement compose" in {
    def compose[A, B, C](f: A => B, g: B => C): A => C =
      (a: A) => g(f(a))

    val f = (a: Int) => a.toString
    val g = (b: String) => b.equals("1")
    compose(f, g)(1) should be(true)
    compose(f, g)(2) should be(false)
  }

}

