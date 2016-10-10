package com.knowbag.statistics

/**
  * Created by dev-williame on 10/10/16.
  */
object Day1 extends App {
  println(Vector(3, 7, 8, 5, 12, 14, 21, 13, 18).sorted)
  println(quartile(Vector(6, 7, 15, 36, 39, 40, 41, 42, 43, 47, 49).sorted))
  println(quartile(Vector(3, 7, 8, 5, 12, 14, 21, 13, 18).sorted))

  /**
    * Consist of split a sorted dataset in 4, splitting by the median,
    * if the dataset has odd number of elements, the median
    * element is removed
    *
    * return three medians
    *
    * @param x
    * @return
    */
  def quartile(x: IndexedSeq[Int]): (Int, Int, Int) = {
    val m = median(x.size, x)
    val (l, r) = split(m, x)
    (median(l.size, l), m, median(r.size, r))
  }

  private def split(m: Double, x: IndexedSeq[Int]) =
    (x.takeWhile(i => i < m), x.dropWhile(i => i <= m))

  private def median(n: Int, values: Seq[Int]): Int = {
    if (values.size % 2 == 0)
      (values(n / 2) + values((n / 2) - 1)) / 2
    else
      values(n / 2)
  }
}
