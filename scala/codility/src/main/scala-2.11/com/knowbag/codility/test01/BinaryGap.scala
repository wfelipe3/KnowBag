package com.knowbag.codility.test01

/**
  * Created by dev-williame on 10/6/16.
  */
object BinaryGap extends App {

  print(solution(529))

  def solution(n: Int): Int = {
    val (_, gap) = toBinary(n).foldLeft((0, Seq[Int]())) { (gap, i) =>
      val (z, zc) = gap
      if (i == 1)
        (0, z +: zc)
      else
        (z + 1, zc)
    }
    gap.max
  }

  private def toBinary(n: Int, b: Seq[Int] = Seq()): Seq[Int] = {
    if (n == 1 || n == 0)
      n +: b
    else
      toBinary(n / 2, (n % 2) +: b)
  }
}
