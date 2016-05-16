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

    def variance(xs: Seq[Double]): Option[Double] =

  }


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
  }

  case class Some[+A](get: A) extends Option[A]

  object None extends Option[Nothing]

}

