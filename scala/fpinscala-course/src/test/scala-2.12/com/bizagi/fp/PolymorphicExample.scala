package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 1/25/17.
  */
class PolymorphicExample extends FreeSpec with Matchers {

  "Test sum polymorphic" - {

    def sumValues[A](a: A, b: A)(implicit f: (A, A) => A): A = f(a, b)
    implicit val sumInts = (x: Int, y: Int) => x + y
    implicit val sumString = (x: String, y: String) => x + y

    def test[A, B](a: A, b: B)(implicit f: (A, B) => A) = f(a,b)
    test(1,2) should be(3)

    sumValues(1,2) should be(3)
    sumValues(1,2)((x, y) => x * y) should be(2)
    sumValues("hello", "world") should be("helloworld")
  }
}
