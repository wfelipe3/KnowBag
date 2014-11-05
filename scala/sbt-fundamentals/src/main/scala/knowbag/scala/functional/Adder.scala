package knowbag.scala.functional

/**
 * Created by feliperojas on 4/11/14.
 */
object Adder {

  def tailSum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }
    loop(a, 0)
  }

  def recursiveSum(f: Int => Int, a: Int, b: Int): Int = {
    if (a > b) 0 else f(a) + recursiveSum(f, a + 1, b)
  }

}
