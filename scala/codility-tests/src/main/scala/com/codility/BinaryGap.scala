package com.codility

/**
  * Created by feliperojas on 8/20/17.
  */
object BinaryGap {

  type Binary = Seq[Int]

  def solution(n: Int): Int = {
    val gaps = findGaps(toBinary(n))
    if (gaps.isEmpty) 0
    else gaps.max
  }

  def toBinary(n: Int): Binary = {
    if (n < 0) Seq()
    else if (n == 0 || n == 1) Seq(n)
    else toBinary(n / 2) ++ toBinary(n % 2)
  }

  def findGaps(binary: Binary): Seq[Int] = {
    def findGaps(binary: Binary, gaps: Seq[Int], open: Boolean, counter: Int): Seq[Int] = {
      if (binary.isEmpty) gaps
      else if (!open && binary.head == 1)
        findGaps(binary.tail, gaps, open = true, 0)
      else if (open && binary.head == 1)
        findGaps(binary, counter +: gaps, open = false, 0)
      else if (open && binary.head == 0)
        findGaps(binary.tail, gaps, open = true, counter + 1)
      else
        findGaps(binary.tail, gaps, open = false, 0)
    }

    findGaps(binary, Seq(), open = false, 0).filter(_ != 0)
  }
}
