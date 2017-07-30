package test

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 2/10/17.
  */
class ImplicitTest extends FreeSpec with Matchers {

  "test implicit conversion" in {
    implicit def intToBoolean(a: Int): Boolean = if (a == 1) true else false

    def test(boolean: Boolean) = println(boolean)

    test(1)
    test(0)
  }

  "test "
}
