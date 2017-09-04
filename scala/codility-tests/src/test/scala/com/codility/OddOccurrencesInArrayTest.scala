package com.codility

import com.codility.OddOccurrencesInArray.unpaired
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 8/20/17.
  */
class OddOccurrencesInArrayTest extends FreeSpec with Matchers {

  "get unpared element" in {
    unpaired(Array(1, 1, 3)) should be(3)
    unpaired(Array(9, 3, 9, 3, 9, 7, 9)) should be(7)
  }


}
