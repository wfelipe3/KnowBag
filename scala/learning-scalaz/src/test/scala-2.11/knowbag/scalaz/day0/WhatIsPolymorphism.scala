package knowbag.scalaz.day0

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 11/1/16.
  */
class WhatIsPolymorphism extends FlatSpec with Matchers {

  "ad-hoc polymorphism" should "search for transforming functions in the scope" in {
    trait Plus[A] {
      def plus(a1: A, a2: A): A
    }

    implicit val plusInt = new Plus[Int] {
      def plus(a1: Int, a2: Int): Int = a1 + a2
    }

    def plus[A: Plus](a1: A, a2: A): A = implicitly[Plus[A]].plus(a1, a2)

    plus(1, 2) should be(3)
  }
}
