package com.test

/**
  * Created by feliperojas on 10/1/16.
  */
object Main extends App {

  def greatest(s: String): Int = {
    val passwords = s.split("\\d+")
      .filter(s => s.matches(".*[A-Z].*"))
      .map(s => s.length)

    if (passwords.isEmpty) -1
    else passwords.max
  }

  println(greatest("a0Ba"))
  println(greatest("55M89aaPPu7"))
  println(greatest("55M89aaPPu7"))
  println(greatest(""))
}
