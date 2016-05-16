package knowbag.fpinscala.chap4

import org.scalatest.{Matchers, FlatSpec}

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

}

