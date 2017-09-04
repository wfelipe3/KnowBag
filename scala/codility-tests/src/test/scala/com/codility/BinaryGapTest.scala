package com.codility

import com.codility.BinaryGap.{Binary, findGaps, toBinary}
import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by feliperojas on 8/20/17.
  */
class BinaryGapTest extends FreeSpec with Matchers {

  "get binary value from int" in {
    toBinary(-1) should be(Seq())
    toBinary(0) should be(Seq(0))
    toBinary(1) should be(Seq(1))
    toBinary(2) should be(Seq(1, 0))
    toBinary(3) should be(Seq(1, 1))
    toBinary(4) should be(Seq(1, 0, 0))
    toBinary(8) should be(Seq(1, 0, 0, 0))
    toBinary(9) should be(Seq(1, 0, 0, 1))
    toBinary(16) should be(Seq(1, 0, 0, 0, 0))
  }

  "find gap value" in {
    findGaps(Seq(1)) should be(Seq())
    findGaps(Seq(0)) should be(Seq())
    findGaps(Seq(1, 0)) should be(Seq())
    findGaps(Seq(1, 1)) should be(Seq())
    findGaps(Seq(1, 0, 1)) should be(Seq(1))
    findGaps(Seq(1, 0, 0, 1)) should be(Seq(2))
    findGaps(Seq(1, 0, 0, 1, 0, 0)) should be(Seq(2))
    findGaps(Seq(1, 0, 0, 1, 0, 0, 1)) should be(Seq(2, 2))
  }

  "get biggest gap" in {
    BinaryGap.solution(0) should be(0)
  }


}
