package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

import scala.annotation.tailrec

/**
  * Created by dev-williame on 1/19/17.
  */
class RecursionExample extends FreeSpec with Matchers {

//  "In FP we dont usually have for loops, while loops, or other control statements, " +
//    "but we have functions instead that is why we see recursion every where" - {
//    def factorial(n: Int): Int =
//      if (n == 0 || n == 1)
//        1
//      else
//        n * factorial(n - 1)
//
//    factorial(2) should be(List(1, 2).product)
//    //factorial(100000) should be((1 to 100000).product)
//  }
//
//  "When using recursion we can run out of stack easily that is why exists tail call optimization or tail recursion" - {
//    def factorial(n: Int): BigInt = {
//      @tailrec
//      def loop(accum: BigInt, n: Int): BigInt = {
//        if (n == 0 || n == 1)
//          accum
//        else
//          loop(accum * n, n - 1)
//      }
//      loop(1, n)
//    }
//
//    factorial(2) should be(List(1, 2).product)
//    factorial(100000) should be((1 to 100000).product)
//  }
}
