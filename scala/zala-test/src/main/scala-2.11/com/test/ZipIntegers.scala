package com.test

/**
  * Created by feliperojas on 10/1/16.
  */
object ZipIntegers extends App {

  println(zip(123.toString, 456.toString))
  println(zip(12.toString, 56.toString))
  println(zip(56.toString, 12.toString))
  println(zip(12345.toString, 678.toString))
  println(zip(1234.toString, 0.toString))
  println(zip(0.toString, 0.toString))
  println(zip(10000000.toString, 0.toString))
  println(zip(1.toString, 100000000.toString))

  def zip(s1: String, s2: String, acum: String = ""): Int = {
    if (s1.isEmpty && s2.isEmpty) {
      val value: Int = acum.toInt
      if (value > 100000000) -1
      else value
    }
    else if (s1.isEmpty)
      zip(s1, s2.tail, acum + s2.head.toString)
    else if (s2.isEmpty)
      zip(s1.tail, s2, acum + s1.head.toString)
    else
      zip(s1.tail, s2.tail, acum + s1.head.toString + s2.head.toString)
  }

}
