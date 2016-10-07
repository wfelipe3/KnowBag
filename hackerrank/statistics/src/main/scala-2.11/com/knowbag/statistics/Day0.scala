package com.knowbag.statistics

/**
  * Created by dev-williame on 10/7/16.
  */
object Day0 {

  def main(args: Array[String]) = {
    //    val n = args(0).toInt
    //    val values = args(1).split(" ").map(_.toInt)
    print(calculateStatistics(10, List(64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120, 51135, 67060).sorted))
  }

  private def calculateStatistics(n: Int, values: Seq[Int]) = {
    val mean = calculateMean(values)
    val median = calculateMedian(n, values)
    val mode = calculateMode(values)
    (mean, median, mode)
  }

  private def calculateMean(values: Seq[Int]): Double = {
    values.sum / values.size.toDouble
  }

  private def calculateMedian(n: Int, values: Seq[Int]): Double = {
    println(values(n / 2), values((n / 2) - 1))
    if (values.size % 2 == 0)
      (values(n / 2) + values((n / 2) - 1)) / 2.0
    else
      values(n / 2).toDouble
  }

  private def calculateMode(values: Seq[Int]): Double = {
    val right: Map[Int, Int] = values.foldRight(Map.empty[Int, Int]) { (i, acc) =>
      acc.get(i).map(a => acc + (i -> (a + 1))).getOrElse(acc + (i -> 1))
    }
    val max = right.maxBy(kv => kv._2)._2
    right.filter(kv => kv._2 == max).minBy(kv => kv._1)._1
  }
}
