package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 3/1/17.
  */
class MonoidExample extends FreeSpec with Matchers {

  "monoid" in {

    val sumMonoid = new Monoid[Int] {
      override def zero: Int = 0
      override def op(a1: Int, a2: Int): Int = a1 + a2
    }
    val multMonoid = new Monoid[Int] {
      override def zero: Int = 1
      override def op(a1: Int, a2: Int): Int = a1 * a2
    }

    def funMonoid[A] = new  Monoid[A => A]  {
      override def zero: (A) => A = a => a
      override def op(a1: (A) => A, a2: (A) => A): (A) => A = a1.compose(a2)
    }

    val op1 : Int => Int = a => a + 1
    val op2 : Int => Int = a => a + 2

    val out  = funMonoid.op(op1, op2)(1)
    println(out)

    println (funMonoid.op(op1, funMonoid.zero)(1))

    List(1,2,3,4,5,6,7,78).par.foldLeft(sumMonoid.zero)(sumMonoid.op)
  }

  "monoid-based fold" in {
    def foldMonoid[A] (list: List[A]) (implicit m: Monoid[A]) = {
      list.foldLeft(m.zero)(m.op)
    }

    implicit val sumMonoid = new Monoid[Int] {
      override def zero: Int = 0
      override def op(a1: Int, a2: Int): Int = a1 + a2
    }

    println(foldMonoid(List(1,2,3,4,5,6,7,78)))
  }

  trait Semigroup[A] {
    def op(a1: A, a2: A): A
  }

  trait Monoid[A] extends Semigroup[A]{
    def zero: A
  }

}
