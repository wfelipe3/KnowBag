package com.codility

import scala.collection.mutable

/**
  * Created by feliperojas on 8/20/17.
  */
object OddOccurrencesInArray {
  def unpaired(v: Array[Int]): Int = {
    def unpaired(v: Array[Int], values: mutable.Map[Int, Int]): Int = {
      if (v.isEmpty) {
        values
          .find(kv => kv._2 == 1)
          .map(_._1)
          .getOrElse(0)
      }
      else {
        val head = v.head
        val tail = v.tail
        val x = values.getOrElse(head, 0) + 1
        if (x == 1)
          values.put(head, x)
        else
          values.remove(head)
        unpaired(tail, values)
      }
    }

    unpaired(v, mutable.Map.empty)
  }
}
