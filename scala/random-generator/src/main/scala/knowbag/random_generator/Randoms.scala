package knowbag.random_generator

import scala.util.Random

/**
 * Created by feliperojas on 25/04/15.
 */
object Randoms {

  def single[T](x: T): RandomGenerator[T] = new RandomGenerator[T] {
    def generate: T = x
  }

  def choose(lo: Int, hi: Int): RandomGenerator[Int] = {
    for (x <- getIntGenerator) yield lo + x % (hi - lo)
  }

  def oneOf[T](xs: T*): RandomGenerator[T] = {
    for (idx <- choose(0, xs.length)) yield xs(idx)
  }

  def getIntGenerator: RandomGenerator[Int] = {
    new RandomGenerator[Int] {
      val random = new Random

      def generate: Int = random.nextInt()
    }
  }
}
