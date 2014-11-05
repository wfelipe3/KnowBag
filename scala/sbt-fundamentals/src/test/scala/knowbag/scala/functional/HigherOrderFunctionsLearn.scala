package knowbag.scala.functional

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import Adder.tailSum
import Adder.recursiveSum
/**
 * Created by feliperojas on 4/11/14.
 */
@RunWith(classOf[JUnitRunner])
class HigherOrderFunctionsLearn extends FunSuite {

  test("count numbers from 1 to 10") {
    assert(tailSum((x: Int) => x, 1, 10) === 1+2+3+4+5+6+7+8+9+10)
  }

  test("recursive count numbers from 1 to 10") {
    assert(recursiveSum((x: Int) => x, 1, 10) === 1+2+3+4+5+6+7+8+9+10)
  }

  test("count numbers from 2 to 8") {
    assert(tailSum((x: Int) => x, 2, 8) === 2+3+4+5+6+7+8)
  }

  test("count square from 3 to 9") {
    assert(tailSum((x: Int) => x * x, 3, 9) === 3*3 + 4*4 + 5*5 + 6*6 + 7*7 + 8*8 + 9*9)
  }

}
