package com.test

/**
  * Created by feliperojas on 10/1/16.
  */
object Elevator extends App {

  def solution(a: Array[Int], b: Array[Int], m: Int, x: Int, y: Int): Int = {

    def countFloors(a: Array[Int], b: Array[Int], count: Int): Int = {
      if (a.isEmpty)
        count
      else {
        val (a1, b1, count1) = round(a, b, Set(), 0, 0)
        countFloors(a1, b1, count + count1)
      }
    }

    def round(a: Array[Int], b: Array[Int], floors: Set[Int], people: Int, weight: Int): (Array[Int], Array[Int], Int) = {
      if (a.isEmpty || people == x || a.head + weight > y)
        (a, b, floors.size + 1)
      else
        round(a.tail, b.tail, floors + b.head, people + 1, weight + a.head)
    }

    countFloors(a, b, 0)
  }


  print(solution(Array(40, 40, 100, 80, 20), Array(3, 3, 2, 2, 3), 3, 5, 200))
  println(solution(Array(40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100,
    40, 90, 150, 80, 60, 40, 40, 20, 10, 15, 78, 65, 53, 44, 40, 100),
    Array(40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60,
      40, 60, 50, 80, 60, 40, 40, 20, 10, 15, 25, 52, 53, 44, 40, 60)
    , 70, 5, 200))

}
