package com.codility

import scala.collection.SortedSet

/**
  * Created by feliperojas on 8/27/17.
  */
object PermMissingElem {

  def search(a: Array[Int]): Int = {
    val ints = SortedSet(a: _*)
    val (_, missing, found) = ints.foldLeft((0, 0, false)) { (z, e) =>
      val (last, missing, found) = z
      if (last + 1 == e) (e, missing, found)
      else (e, last, true)
    }
    if (found) missing + 1
    else a.length + 1
  }

}
