package knowbag.scala.functional

/**
 * Created by feliperojas on 17/02/15.
 */
object Sum {

  def sum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, accum: Int): Int = {
      if (a > b)
        accum
      else
        loop(a + 1, f(a) + accum)
    }
    loop(a, 0)
  }

  def product(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 1 else f(a) * product(f)(a - 1, b)
}
