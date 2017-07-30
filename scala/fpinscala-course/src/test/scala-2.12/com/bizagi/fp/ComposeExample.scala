package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 2/1/17.
  */
class ComposeExample extends FreeSpec with Matchers {

//  "implement compose" - {
//    def compose[A, B, C](g: B => C, f: A => B): A => C =
//      a => g(f(a))
//
//    def andThen[A, B, C](f: A => B, g: B => C): A => C =
//      compose(g, f)
//
//    val g: Int => String = i => i.toString
//    val f: String => Int = x => x.size
//
//    val compose1: String => String = compose(g, f)
//    val compose2: String => String = andThen(f, g)
//    compose1("test") should be("5")
//
//    g.compose(f)
//  }
}
