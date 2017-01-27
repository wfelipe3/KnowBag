package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 1/25/17.
  */
class CurryExample extends FreeSpec with Matchers {

  "curry" - {
    def curry[A, B, C](f: (A, B) => C): A => B => C =
      a => b => f(a,b)

    curry((x:Int,y:Int)=>x+y)(1)(2) should be (3)

    def uncurry[A,B,C](f: A => B => C): (A, B) => C =
      (a, b) => f(a)(b)

    uncurry((x: Int) => (y: Int) => x + y)(1, 3) should be(4)
  }
}
