package com.codility

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 8/27/17.
  */
class FrogJmpTest extends FreeSpec with Matchers {

  "jumping frog" in {
    jumps(10, 85, 30) should be(3)
  }

  def jumps(x: Int, y: Int, d: Int): Int = {
    val div = (y - x) / d
    val mod = if ((y - x) % d != 0) 1 else 0
    div + mod
  }

}
