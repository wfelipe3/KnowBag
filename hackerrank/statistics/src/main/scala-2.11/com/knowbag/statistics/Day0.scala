package com.knowbag.statistics

/**
  * Created by dev-williame on 10/7/16.
  */
object Day0 {

  def main(args: Array[String]) = {
    println(calculateStatistics(10, List(64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120, 51135, 67060).sorted))
    println(weightedMean(5, Seq(10, 40, 30, 50, 20), Seq(1, 2, 3, 4, 5)))
    println(weightedMean(10, Seq(10, 40, 30, 50, 20, 10, 40, 30, 50, 20), Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)))
  }

  private def calculateStatistics(n: Int, values: Seq[Int]) = {
    val me = mean(values)
    val md = median(n, values)
    val mo = mode(values)
    (me, md, mo)
  }

  private def mean(values: Seq[Int]): Double = {
    values.sum / values.size.toDouble
  }

  // The media is the value in the middle of the dataset
  private def median(n: Int, values: Seq[Int]): Double = {
    if (values.size % 2 == 0)
      (values(n / 2) + values((n / 2) - 1)) / 2.0
    else
      values(n / 2).toDouble
  }

  // The mode is the value repeated the most
  private def mode(values: Seq[Int]): Int = {
    val right: Map[Int, Int] = values.foldRight(Map.empty[Int, Int]) { (i, acc) =>
      acc.get(i).map(a => acc + (i -> (a + 1))).getOrElse(acc + (i -> 1))
    }
    val max = right.maxBy(kv => kv._2)._2
    right.filter(kv => kv._2 == max).minBy(kv => kv._1)._1
  }

  // Is the mean using weights for the data set
  private def weightedMean(n: Int, x: Seq[Int], w: Seq[Int]): Double = {
    val wm = (0 until n).map(i => x(i) * w(i)).sum / w.sum.toDouble
    BigDecimal(wm).setScale(1, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

}
