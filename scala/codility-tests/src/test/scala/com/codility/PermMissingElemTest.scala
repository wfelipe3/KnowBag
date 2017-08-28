package com.codility

import com.codility.PermMissingElem.search
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 8/27/17.
  */
class PermMissingElemTest extends FreeSpec with Matchers {

  "search for missing element" in {
    search(Array(2, 3, 1, 5)) should be(4)
    search(Array()) should be(1)
    search(Array(1)) should be(2)
    search(Array(2, 3, 4)) should be(1)
  }


}
