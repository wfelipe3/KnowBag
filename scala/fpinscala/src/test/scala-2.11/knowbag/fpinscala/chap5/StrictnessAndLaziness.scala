package knowbag.fpinscala.chap5

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by WilliamE on 22/06/2016.
  */
class StrictnessAndLaziness extends FlatSpec with Matchers {

  "if function" should "behave as if sentence" in {
    def if2[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
      if (cond) onTrue else onFalse

    val value = if2(cond = false, onTrue = sys.error("fail"), onFalse = 5)
    value should be(5)
  }

  "evaluate an argument" should "not cache by default the value" in {
    var values = Seq[Int]()
    def maybeTwice(b: Boolean, i: => Int) = if (b) i + i else 0
    maybeTwice(true, {
      values = 1 +: values;
      1 + 41
    })
    values should be(Seq(1, 1))
  }

  "lazy" should "evalute the value only once" in {
    var values = Seq[Int]()
    def maybeTwice(b: Boolean, i: => Int) = {
      lazy val j = i
      if (b) j + j else 0
    }
    maybeTwice(true, {
      values = 1 +: values;
      1 + 41
    })
    maybeTwice(false, {
      values = 1 +: values;
      1 + 41
    })
    values should be(Seq(1))
  }

  "headOption" should "retorn the head of the stream without processing the tail" in {
    val head = Stream(1, 2, 3, 4).headOption
    head should be(Some(1))
  }

  "exercise 5.1" should "convert a Stream to a list" in {
    Stream(1, 2, 3, 4, 5, 6, 7).toList should be(Seq(1, 2, 3, 4, 5, 6, 7))
  }

  behavior of "exercise 5.2"
  it should "implement take n" in {
    Stream().take(4) should be(Empty)
    Stream(1, 2, 3).take(0) should be(Empty)
    Stream(1, 2, 3, 4, 5, 6, 7).take(3).toList should be(Seq(1, 2, 3))
    Stream(1, 2).take(3).toList should be(Seq(1, 2))
  }
  it should "implement drop n" in {
    Stream().drop(5) should be(Empty)
    Stream(1, 2, 3).drop(0).toList should be(Seq(1, 2, 3))
    Stream(1, 2, 3, 4, 5, 6, 7).drop(3).toList should be(Seq(4, 5, 6, 7))
  }

  "Exercise 5.3" should "implement take while" in {
    Stream[Int]().takeWhile(_ > 3) should be(Empty)
    Stream(1, 2, 3).takeWhile(_ > 4) should be(Empty)
    Stream(1, 2, 3, 4, 5, 6, 7).takeWhile(_ > 3) should be(Empty)
    Stream(1, 2, 3, 4, 5, 6, 7).takeWhile(_ < 4).toList should be(Seq(1, 2, 3))
  }

  sealed trait Stream[+A] {

    def headOption: Option[A] = this match {
      case Empty => None
      case Cons(h, t) => Some(h())
    }

    def toList: Seq[A] = this match {
      case Empty => Nil
      case Cons(h, tl) => h() +: tl().toList
    }

    def take(n: Int): Stream[A] = this match {
      case Empty => Empty
      case Cons(h, tl) if n > 0 => Cons(h, () => tl().take(n - 1))
      case _ => Empty
    }

    def drop(n: Int): Stream[A] = this match {
      case Empty => Empty
      case Cons(h, t) if n > 0 => t().drop(n - 1)
      case Cons(h, t) => Cons(h, () => t().drop(n))
    }

    def takeWhile(p: A => Boolean): Stream[A] = this match {
      case Empty => Empty
      case Cons(h, t) if p(h()) => Cons(h, () => t().takeWhile(p))
      case _ => Empty
    }
  }

  case object Empty extends Stream[Nothing]

  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

  object Stream {
    def cons[A](hd: => A, t1: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = t1
      Cons(() => head, () => tail)
    }

    def empty[A]: Stream[A] = Empty

    def apply[A](as: A*): Stream[A] =
      if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))
  }

}
