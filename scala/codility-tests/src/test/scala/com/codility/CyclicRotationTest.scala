package com.codility

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 8/21/17.
  */
class CyclicRotationTest extends FreeSpec with Matchers {

  "test rotate array one time" in {
    rotate(Array(1, 2), 1) should be(Array(2, 1))
    rotate(Array(1, 2, 3), 1) should be(Array(3, 1, 2))
    rotate(Array(1, 2, 3), 2) should be(Array(2, 3, 1))
    rotate(Array(), 3) should be(Array())
  }

  def rotate(array: Array[Int], k: Int): Array[Int] = {
    def rotateInner(array: Array[Int]): Array[Int] =
      Array(array.last) ++ array.init

    if (array.isEmpty) array
    else if (k == 0) array
    else rotate(rotateInner(array), k - 1)
  }

}
