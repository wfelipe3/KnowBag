package knowbag.scala.functional

import java.lang.Math.abs

import org.scalatest.FunSuite

/**
 * Created by feliperojas on 18/02/15.
 */
class FixedPointLearn extends FunSuite {

  test("create fixedpoint") {

    def isCloseEnough(x: Double, y: Double) = {
      abs((x - y) / x) / x < 0.0001
    }

    def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
      def iterate(guess: Double): Double = {
        println(guess)
        val next = f(guess)
        if (isCloseEnough(guess, next)) next
        else iterate(next)
      }
      iterate(firstGuess)
    }

    assert(fixedPoint(x => 1 + x / 2)(1) === 1.999755859375)

    def sqrt(x: Double): Double = {
      fixedPoint(y => (y + x / y) / 2)(1)
    }

    def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

    def sqrtA(x: Double) = fixedPoint(averageDamp(y => x / y))(1)

    assert(sqrt(2) === 1.4142135623746899)
    assert(sqrtA(2) === 1.4142135623746899)
  }
}
