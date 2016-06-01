package knowbag.fpinscala.chap4

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by WilliamE on 06/05/2016.
  */
class ErrorHandlingWithoutExceptions extends FlatSpec with Matchers {

  "Option.map" should "return new Option with mapped value or none if is none" in {
    Some(10).map(_.toString) should be(Some("10"))
    None.map(_.toString) should be(None)
  }

  "Exercise 4.2" should "implement variance function in terms of flatmap" in {
    variance(Seq(1, 2)) should be(Some(0.25))
  }

  "Exercise 4.3" should "implement map 2 for Option" in {
    val sum: (Int, Int) => Int = (a, b) => a + b
    val sumOption: Option[Int] = map2(Some(1), Some(2))(sum)
    sumOption should be(Some(3))
  }

  "Exercise 4.4" should "implement sequence that combines a list of options into an option containing a list" in {
    sequence(List(Some(1), Some(2), Some(3))) should be(Some(List(1, 2, 3)))
    sequence(List(Some(1), None, Some(3))) should be(None)
  }

  "Exercise 4.5" should "implement traverse" in {
    traverse(List("1", "2", "3", "4"))(a => Try(a.toInt)) should be(Some(List(1, 2, 3, 4)))
    traverse(List("1", "test"))(a => Try(a.toInt)) should be(None)
  }

  "Exercise 4.6" should "implement functions in Either" in {
    Either.Try(10).map(_ + 10) should be(Right(20))
    Right("test").map2(Right(" this is"))(_ + _) should be(Right("test this is"))
  }

  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  def variance(xs: Seq[Double]): Option[Double] =
    mean(xs) flatMap (m => mean(xs.map(x => Math.pow(x - m, 2))))

  sealed trait Option[+A] {
    def map[B](f: A => B): Option[B] =
      this match {
        case Some(a) => Some(f(a))
        case None => None
      }


    def flatMap[B](f: A => Option[B]): Option[B] =
      this match {
        case Some(a) => f(a)
        case None => None
      }

    def getOrElse[B >: A](default: => B): B =
      this match {
        case Some(a) => a
        case None => default
      }

    def orElse[B >: A](default: => Option[B]): Option[B] =
      this match {
        case Some(a) => this
        case None => default
      }

    def filter(f: A => Boolean): Option[A] =
      this match {
        case Some(a) if f(a) => this
        case _ => None
      }

    def lift[A, B](f: A => B): Option[A] => Option[B] = _ map f
  }

  case class Some[+A](get: A) extends Option[A]

  object None extends Option[Nothing]

  def Try[A](a: => A): Option[A] =
    try
      Some(a)
    catch {
      case e: Exception => None
    }

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    a.flatMap(va => b.map(vb => f(va, vb))).orElse(None)

  def sequence[A](a: List[Option[A]]): Option[List[A]] =
    traverse(a)(v => v)

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
    a.foldRight(Try(List[B]()))((av, b) => f(av).flatMap(e => b.map(i => e :: i)))
}

sealed trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B] =
    this match {
      case Right(v) => Right(f(v))
      case Left(e) => Left(e)
    }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] =
    this match {
      case Right(v) => f(v)
      case Left(e) => Left(e)
    }

  def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] =
    this match {
      case Right(v) => Right(v)
      case Left(e) => b
    }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
    this match {
      case Right(v) => b.map(bv => f(v, bv))
      case Left(e) => Left(e)
    }
}

object Either {
  def Try[A](a: => A): Either[Exception, A] =
    try Right(a)
    catch {
      case e: Exception => Left(e)
    }
}

case class Left[+E](values: E) extends Either[E, Nothing]

case class Right[+A](value: A) extends Either[Nothing, A]


