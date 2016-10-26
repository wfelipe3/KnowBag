package com.knowbag.statistics

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 10/25/16.
  */
class StatsTest extends FlatSpec with Matchers {

  behavior of "stats"

  it should "calculate mean" in {
    Stats.mean(Seq(1, 2, 3, 4, 5)) should be((1 + 2 + 3 + 4 + 5) / 5)
  }

  it should "calculate median" in {
    Stats.median(Seq(1, 2, 3, 4, 5)) should be(3)
    Stats.median(Seq(1, 2, 3, 4, 5, 6)) should be((3 + 4) / 2.0)
  }

  it should "calculate mode" in {
    Stats.mode(Seq(1, 1, 2, 3)) should be(1)
    Stats.mode(Seq(1, 1, 2, 2, 3)) should be(1)
    Stats.mode(Seq(1, 1, 2, 2, 3, 3, 3)) should be(3)
  }

  it should "calculate weighted mean" in {
    Stats.weightedMean(Seq[(Double, Double)]((10, 1), (40, 2), (30, 3), (50, 4), (20, 5))) should be(32.0)
  }

  it should "calculate quartile" in {
    Stats.quartile(Vector[Double](6, 7, 15, 36, 39, 40, 41, 42, 43, 47, 49)) should be((15.0, 40.0, 43.0))
    Stats.quartile(Vector[Double](3, 7, 8, 5, 12, 14, 21, 13, 18)) should be((6.0,12.0,16.0))
    Stats.quartile(Vector[Double](3, 7, 8, 5, 14, 21, 13, 18)) should be((6.0,10.5,16.0))
  }

}
