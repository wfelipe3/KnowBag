package knowbag.fpinscala.chap3

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 10/24/15.
 */
class FunctionalDataStructure extends FlatSpec with Matchers {

  "exercise 3.1" should "Value for list in pattern matching" in {
    val x = List(1, 2, 3, 4, 5) match {
      case Cons(y, Cons(2, Cons(4, _))) => y
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + List.sum(t)
      case _ => 101
    }

    x should be(3)
  }

  "exercise 3.2" should "implement tail function" in {
    an[NilListException] shouldBe thrownBy(List.tail(Nil))
    List.tail(List(1)) should be(Nil)
    List.tail(List(3, 4)) should be(List(4))
    List.tail(List(1, 2, 6, 34, 5)) should be(List(2, 6, 34, 5))
  }

  "exercise 3.3" should "implement set head function" in {
    List.setHead(2, Nil) should be(List(2))
    List.setHead(10, List(2, 3, 4, 5)) should be(List(10, 3, 4, 5))
  }

  "exercise 3.4" should "implement drop function" in {
    List.drop(0, List(10)) should be(List(10))
    List.drop(1, List("20")) should be(Nil)
    List.drop(5, List(1, 2, 3, 4, 45, 5, 6, 7, 9)) should be(List(5, 6, 7, 9))
    an[NilListException] shouldBe thrownBy(List.drop(10, List(true, false)))
  }

  "exercise 3.5" should "implement drop while" in {
    List.dropWhile(Nil)(num => true) should be(Nil)
    List.dropWhile(List(34))(num => false) should be(List(34))
    List.dropWhile(List(10))(num => true) should be(Nil)
    List.dropWhile(List("pedro", "gomez", "lopez"))(_.endsWith("o")) should be(List("gomez", "lopez"))
  }

  "exercise 3.6" should "implement init" in {
    List.init(List(1, 2, 3, 4)) should be(List(1, 2, 3))
    List.init(List(1, 2, 3)) should be(List(1, 2))
    List.init(List(1, 2)) should be(List(1))
    List.init(List(1)) should be(Nil)
    List.init(Nil) should be(Nil)
  }

  "fold right" should "reduce values to de right" in {
    List.foldRight(List[Int](), 0)(_ + _) should be(0)
    List.foldRight(List(1), 0)(_ + _) should be(1)
    List.foldRight(List(1, 2), 0)(_ + _) should be(3)
    List.foldRight(List(1, 2), 1)(_ + _) should be(4)
    List.foldRight(List(1, 2), 1)(_ * _) should be(2)
    List.foldRight(List(1, 2), 0)(_ * _) should be(0)
  }

  it should "throw stackOverFlowException when the list is too big" in {
    an[StackOverflowError] shouldBe thrownBy(List.length(createList(1000000)))
  }

  "exercise 3.8" should "pass Nil and cons to foldRight" in {
    List.foldRight(List(1, 2, 3), Nil: List[Int])(Cons(_, _)) should be(List(1, 2, 3))
  }

  "exercise 3.9" should "implement length with fold right" in {
    List.length(List()) should be(0)
    List.length(List("test")) should be(1)
    List.length(List(true, false)) should be(2)
    List.foldRight(List("this", "is", "a", "test"), "")(_ + " " + _).trim should be("this is a test")
  }

  "exercise 3.10" should "implement fold left tail recursive" in {
    List.foldLeft(Nil: List[Int], 0)(_ + _) should be(0)
    List.foldLeft(List(1), 0)(_ + _) should be(1)
    List.foldLeft(List("this", "is", "a", "test"), "")(_ + " " + _).trim should be("this is a test")
    Seq("this", "is", "a", "test").foldLeft("")(_ + " " + _).trim should be("this is a test")
    List.foldLeft(createList(1000000), 0)((b, a) => b + 1) should be(1000000)
  }

  "exercise 3.11" should "implement sum product and length with fold left" in {
    List.foldLeft(List(1, 2, 3, 4, 4, 5, 98), 1)(_ * _) should be(1 * 2 * 3 * 4 * 4 * 5 * 98)
    List.foldLeft(List(1, 2, 3, 4, 4, 5, 98), 0)(_ + _) should be(1 + 2 + 3 + 4 + 4 + 5 + 98)
    List.foldLeft(List(1, 2, 3, 4, 4, 5, 98), 0)((length, b) => length + 1) should be(7)
  }

  "exercise 3.12" should "implement reverse" in {
    List.reverse(Nil) should be(Nil)
    List.reverse(List(1)) should be(List(1))
    List.reverse(List(1, 5)) should be(List(5, 1))
  }

  "exercise 3.13" should "implement fold left in terms of fold right" in {
    List.foldLeftR(Nil: List[Int], 0)(_ + _) should be(0)
    List.foldLeftR(List(1), 0)(_ + _) should be(1)
  }

  def createList(size: Int): List[Int] = {
    def createList(l: List[Int], n: Int): List[Int] = {
      if (n == size) l
      else createList(Cons(0, l), n + 1)
    }
    createList(Nil: List[Int], 0)
  }

  trait List[+A]

  object List {
    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

    def sum(as: List[Int]): Int = as match {
      case Nil => 0
      case Cons(head, tail) => head + sum(tail)
    }

    def tail[A](as: List[A]): List[A] = as match {
      case Nil => throw new NilListException
      case Cons(x, tail) => tail
    }

    def setHead[A](newHead: A, as: List[A]): List[A] = as match {
      case Nil => Cons(newHead, Nil)
      case Cons(_, tail) => Cons(newHead, tail)
    }

    def drop[A](n: Int, as: List[A]): List[A] = {
      if (n == 0) as
      else as match {
        case Nil => throw new NilListException
        case Cons(_, tail) => drop(n - 1, tail)
      }
    }

    def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
      case Cons(head, tail) if f(head) => dropWhile(tail)(f)
      case _ => l
    }

    def init[A](l: List[A]): List[A] = l match {
      case Nil => Nil
      case Cons(head, Nil) => Nil
      case Cons(head, tail) => Cons(head, init(tail))
    }

    def foldRight[A, B](l: List[A], b: B)(f: (A, B) => B): B = l match {
      case Nil => b
      case Cons(head, tail) => f(head, foldRight(tail, b)(f))
    }

    def foldLeft[A, B](l: List[A], b: B)(f: (B, A) => B): B = l match {
      case Nil => b
      case Cons(head, tail) => foldLeft(tail, f(b, head))(f)
    }

    def foldLeftR[A, B](list: List[A], b: B)(f: (A, B) => B): B = ???

    def length[A](l: List[A]): Int = {
      foldRight(l, 0)((a, b) => b + 1)
    }

    def reverse[A](l: List[A]): List[A] = {
      foldLeft(l, Nil: List[A])((list, value) => Cons(value, list))
    }
  }

  class NilListException extends Exception

  case object Nil extends List[Nothing]

  case class Cons[+A](head: A, tail: List[A]) extends List[A]

}
