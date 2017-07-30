package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

import scala.util.Try

/**
  * Created by dev-williame on 2/22/17.
  */
class ErrorExample extends FreeSpec with Matchers {

  "div by 0" in {
    def div(i: Int, n: Int): Option[Int] = if (n == 0) None else Some(i / n)
    def multi(i: Int, n: Int): Option[Int] = Some(i * n)

    val value = div(4, 2)
    val newValue = value.map(_ * 2)
    val doubleDiv = value.flatMap(i => multi(i, 2))
    doubleDiv.filter(_ % 2 == 0)

    value match {
      case Some(x) => println(x)
      case None => println("nothing")
    }

    val finalValue = doubleDiv.getOrElse(2)
  }

  "div by 0 with try" in {
    def div(i: Int, n: Int): Try[Int] = Try {
      i / n
    }

    val value = div(4, 2)

    val recovered = value.recover {
      case e: ArithmeticException => e.getMessage
    }.get
  }

  "div by 0 either" in {
    def div(i: Int, n: Int): Either[String, Int] = if (n == 0) Left("div by zero") else Right(i / n)

    val value = div(4, 2)

    value match {
      case Left(v) => print(v)
      case Right(v) => print(v)
    }
  }
}
