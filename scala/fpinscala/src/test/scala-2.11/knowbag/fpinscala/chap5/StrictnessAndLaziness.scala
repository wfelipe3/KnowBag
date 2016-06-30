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

  "headOption" should "return the head of the stream without processing the tail" in {
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

  "Exercise 5.4" should "implement forAll" in {
    Stream[Int]().forAll(_ > 0) should be(false)
    Stream(1, 2, 3).forAll(_ > 4) should be(false)
    Stream(1, 2, 3).forAll(_ < 4) should be(true)
  }

  "Exercise 5.5" should "implement takeWhile with fold right" in {
    Stream[Int]().takeWhileFR(_ > 3) should be(Empty)
    Stream(1, 2, 3).takeWhileFR(_ > 4) should be(Empty)
    Stream(1, 2, 3, 4, 5, 6, 7).takeWhileFR(_ > 3) should be(Empty)
    Stream(1, 2, 3, 4, 5, 6, 7).takeWhileFR(_ < 4).toList should be(Seq(1, 2, 3))
  }

  "Exercise 5.6" should "implement headOption with fold right" in {
    Stream(1, 2, 3, 4).headOptionFR should be(Some(1))
    Stream(2, 3, 4).headOptionFR should be(Some(2))
    Stream().headOptionFR should be(None)
  }

  behavior of "Exercise 5.7"

  it should "implement map" in {
    Stream[Int]().map(_.toString()).toList should be(Nil)
    Stream(1, 2, 3).map(_.toString).toList should be(Seq("1", "2", "3"))
  }

  it should "implement flatMap" in {
    Stream[Int]().flatMap(a => Stream(a + 1)).toList should be(Nil)
    Stream(1, 2, 3).flatMap(a => Stream(a + 1)).toList should be(Seq(2, 3, 4))
  }

  it should "implement filter" in {
    Stream[Int]().filter(_ > 0) should be(Empty)
    Stream(1, 2, 3, 4, 5).filter(_ % 2 == 0).toList should be(Seq(2, 4))
  }

  it should "implement append" in {
    Stream[Int]().append(Stream(1, 2, 4)).toList should be(Seq(1, 2, 4))
    Stream(1, 2, 3).append(Stream(4, 5, 6)).toList should be(Seq(1, 2, 3, 4, 5, 6))
    Stream(1, 2, 3).append(Stream[Int]()).toList should be(Seq(1, 2, 3))
  }

  behavior of "infinite stream"

  it should "evaluate lazily" in {
    val ones: Stream[Int] = Stream.constant(1)
    ones.take(5).toList should be(Seq(1, 1, 1, 1, 1))
    ones.exists(_ % 2 != 0) should be(true)
    ones.map(_ + 1).exists(_ % 2 == 0) should be(true)
    ones.take(1).takeWhile(_ == 1).toList should be(Seq(1))
    ones.take(10).forAll(_ != 1) should be(false)
  }

  "Exercise 5.8" should "implement constant" in {
    Stream.constant(10).take(3).toList should be(Seq(10, 10, 10))
  }

  "Exercise 5.9" should "implement from" in {
    Stream.from(5).take(5).toList should be(Seq(6, 7, 8, 9, 10))
  }

  "Exercise 5.10" should "implement fibs" in {
    Stream.fibs().take(5).toList should be(Seq(0, 1, 1, 2, 3))
  }

  "Exercise 5.11" should "implement unfold" in {
    Stream.unfold(1)(s => Some((s, s + 1))).take(5).toList should be(Seq(1, 2, 3, 4, 5))
    Stream.unfold(1) { s =>
      if (s < 2) Some((s, s + 1))
      else None
    }.take(5).toList should be(Seq(1))
  }

  sealed trait Stream[+A] {

    def headOption: Option[A] = this match {
      case Empty => None
      case Cons(h, t) => Some(h())
    }

    def headOptionFR: Option[A] =
      foldRight(None: Option[A]) { (a, b) => Some(a) }

    def toList: Seq[A] = this match {
      case Empty => Nil
      case Cons(h, tl) => h() +: tl().toList
    }

    def take(n: Int): Stream[A] = this match {
      case Cons(h, tl) if n > 0 => Cons(h, () => tl().take(n - 1))
      case _ => Empty
    }

    def drop(n: Int): Stream[A] = this match {
      case Empty => Empty
      case Cons(h, t) if n > 0 => t().drop(n - 1)
      case Cons(h, t) => Cons(h, () => t().drop(n))
    }

    def takeWhile(p: A => Boolean): Stream[A] = this match {
      case Cons(h, t) if p(h()) => Cons(h, () => t().takeWhile(p))
      case _ => Empty
    }

    def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
      case Cons(h, t) => f(h(), t().foldRight(z)(f))
      case _ => z
    }

    def exists(p: A => Boolean): Boolean =
      foldRight(false)((a, b) => p(a) || b)

    def forAll(p: A => Boolean): Boolean = this match {
      case Empty => false
      case _ => !foldRight(false)((a, b) => !p(a) || b)
    }

    def takeWhileFR(p: A => Boolean): Stream[A] =
      foldRight(Stream[A]()) { (a, b) =>
        if (p(a)) Cons(() => a, () => b)
        else Empty
      }

    def map[B](f: A => B): Stream[B] =
      foldRight(Stream[B]())((a, b) => Cons(() => f(a), () => b))

    def flatMap[B](f: A => Stream[B]): Stream[B] =
      foldRight(Stream[B]())((a, b) => f(a).append(b))

    def filter(p: A => Boolean): Stream[A] =
      foldRight(Stream[A]())((a, b) => if (p(a)) Cons(() => a, () => b) else b)

    def append[B >: A](s: => Stream[B]): Stream[B] =
      foldRight(s)((a, b) => Cons(() => a, () => b))

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

    def constant[A](a: A): Stream[A] = Stream.cons(a, constant(a))

    def from(i: Int): Stream[Int] = Stream.cons(i + 1, from(i + 1))

    def fibs(): Stream[Int] = {
      def fib(prev: Int = 0, actual: Int = 1): Stream[Int] =
        Stream.cons(prev, fib(actual, actual + prev))
      fib()
    }

    def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
      f(z).map(t => Stream.cons(t._1, unfold(t._2)(f))).getOrElse(Empty)
    }
  }

}
