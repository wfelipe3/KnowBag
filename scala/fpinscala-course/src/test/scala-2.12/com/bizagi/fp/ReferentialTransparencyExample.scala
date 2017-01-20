package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 1/19/17.
  */
class ReferentialTransparencyExample extends FreeSpec with Matchers {

  "When using referential transparency you should be able to change the value of a function with the function call" - {
    val sum = (a: Int, b: Int) => a + b
    val five = sum(2, 3)
    val six = 1 + five
    val onePlusFunctionApplication = 1 + sum(2, 3)

    six should be(onePlusFunctionApplication)
  }

  "When we use functions with side effects referential transparency can not be achieved" - {
    def appendWorldSideEffective(s: StringBuilder): String = s.append("world").toString()
    def appendWorldPure(s: String): String = s + "world"

    val hello = "hello"

    val helloWorld = appendWorldPure(hello)
    val helloWorld2 = appendWorldPure(hello)

    //appendWorldPure is referentially transparent and that is why they are the same
    helloWorld should be(helloWorld2)

    val helloImpure = new StringBuilder("hello")
    val helloWorldImpure = appendWorldSideEffective(helloImpure)
    val helloWorldImpure2 = appendWorldSideEffective(helloImpure)

    //appendWorldSideEffective is not referentially transparent given the side effect of modifying the input,
    //that is why they are not the same
    helloWorldImpure should not be helloWorldImpure2
  }
}
