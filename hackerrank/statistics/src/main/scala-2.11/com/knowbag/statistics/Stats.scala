package com.knowbag.statistics

/**
  * Created by dev-williame on 10/25/16.
  */
object Stats {

  def mean(xs: Seq[Double]): Double = xs.sum / xs.size.toDouble

  def median(xs: Seq[Double]): Double = {
    val sorted = xs.sorted
    if (xs.size % 2 == 0)
      (sorted(xs.size / 2) + sorted((xs.size / 2) - 1)) / 2.0
    else
      sorted(xs.size / 2)
  }

  def mode(xs: Seq[Double]): Double = {
    val values = xs.foldLeft(Map.empty[Double, Int]) { (acc, i) =>
      val v = acc.get(i).map(_ + 1).getOrElse(1)
      acc + (i -> v)
    }
    values.toSeq.sortWith { (v1, v2) =>
      if (v1._2.compareTo(v2._2) == 0)
        v1._1.compareTo(v2._1) < 0
      else
        v1._2.compareTo(v2._2) > 0
    }.head._1
  }

  def weightedMean(tuples: Seq[(Double, Double)]): Double = {
    val (m, w) = tuples.foldLeft((0.0, 0.0)) { (acc, t) =>
      val (i, w) = t
      (acc._1 + (i * w), acc._2 + w)
    }
    m / w
  }

  def quartile(xs: Vector[Double]): (Double, Double, Double) = {
    val sorted = xs.sorted
    val q2 = median(sorted)
    val q1 = median(sorted.takeWhile(i => i < q2))
    val q3 = median(sorted.dropWhile(i => i <= q2))
    (q1, q2, q3)
  }

}
