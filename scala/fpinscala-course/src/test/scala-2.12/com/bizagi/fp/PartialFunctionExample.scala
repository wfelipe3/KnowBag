package com.bizagi.fp

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 1/25/17.
  */
class PartialFunctionExample extends FlatSpec with Matchers {

  "given 4 / 2" should "return 2" in {
    def div(x: Int, y: Int): Option[Int] =
      if (y == 0) None
      else Some(x / y)

    div(4, 2) should be(Some(2))
    div(4, 0) should be(None)
  }
}
