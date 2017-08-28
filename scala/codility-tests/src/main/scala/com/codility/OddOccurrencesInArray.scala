package com.codility

/**
  * Created by feliperojas on 8/20/17.
  */
object OddOccurrencesInArray {
  def unpaired(v: Array[Int]): Int = {
    v.foldLeft(Set.empty[Int]) { (s, v) =>
      if (s.contains(v))
        s - v
      else
        s + v
    }.head
  }
}
