package knowbag.fpinscala.chap5

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by WilliamE on 22/06/2016.
  */
class StrictnessAndLaziness extends FlatSpec with Matchers {

  "if function" should "behave as if sentence" in {
    def if2[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
      if (cond) onTrue else onFalse

    val value = if2(cond = false, onTrue = sys.error("fail"), onFalse = 5)
    value should be(5)
  }
}
