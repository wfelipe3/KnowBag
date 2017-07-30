package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 2/15/17.
  */
class CollectionsExample extends FreeSpec with Matchers {

  "scala list" in {
    val list = 1 :: 2 :: 3 :: Nil
    5 +: list :+ 4


    def iter(l: List[Int]): Int = {
      l match {
        case head :: tail =>
          head + iter(tail)
        case Nil => 0
      }
    }

    /* val names = ("camilo" :: "johanna" :: "sebastian" :: Nil).foldLeft(List.empty[String])(_ :: _)
    val names2 = ("camilo" :: "johanna" :: "sebastian" :: Nil).foldRight(List.empty[String])(_ :: _)
    println(names, names2)

    names.filter(_.contains("johanna"))
    names.map(_.length)*/

  }
}
