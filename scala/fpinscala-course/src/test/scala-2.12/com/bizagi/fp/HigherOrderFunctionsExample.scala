package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 1/19/17.
  */
class HigherOrderFunctionsExample extends FreeSpec with Matchers {

  "With higher order functions you can pass functions as parameters to other functions" - {
    val isEven = (x: Int) => x % 2 == 0
    (1 to 100) map (_.toString()) foreach println
    (1 to 100) filter isEven should be(2 to 100 by 2)
  }

  "We can also return functions from functions" - {
    case class User(id: Int, name: String)
    def isUserName(name: String): User => Boolean = (u: User) => u.name == name

    val isFelipe = isUserName("felipe")
    isFelipe(User(123, "felipe")) should be(true)
  }
}
