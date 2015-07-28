
package knowbag.functional.state

import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by feliperojas on 7/14/15.
 */
class RandomGeneratorTest extends FlatSpec with ShouldMatchers {

  "Random generator" should "return random value with next random function in order to make functional state" in {
    val (value, gen): (Int, RG[Int]) = IntRG(42).next
    value should be(IntRG(42).next._1)
    println(IntRG(42).next)
  }

  "non negative generator" should "return between 0 and Int.maxValue" in {
    Stream.from(Int.MinValue).take(10).foreach { x =>
      NonNegativeRG(IntRG(x)).next match {
        case (value, _) =>
          value should be > 0
      }
    }
  }

  "generate a random double that" should "return values between 0 and 1 exclusive" in {
    DoubleRG(IntRG(10)).next match {
      case (value, _) => value.toInt should (be >= 0 and be < 1)
    }
  }

  "generate random number" should "return tuple with 3 values, int double and RG" in {
    IntDoubleRG(IntRG(10)).next match {
      case ((intValue, doubleValue), _) =>
        intValue shouldBe a[java.lang.Integer]
        doubleValue shouldBe a[java.lang.Double]
    }
  }

  "ints generator" should "return a list of generated ints" in {
    def ints(count: Int)(rg: RG[Int]): (List[Int], RG[Int]) = {
      def subInts(actual: Int, values: List[Int])(rg: RG[Int]): (List[Int], RG[Int]) = {
        if (actual == count) (values, rg)
        else subInts(actual + 1, rg.next._1 :: values)(rg.next._2)
      }

      subInts(0, Nil)(rg)
    }

    ints(10)(IntRG(10)) match {
      case (values, _) => println(values)
    }
  }

  case class RNG(seed: Long) {
    type Rand[+A] = RNG => (A, RNG)

    def int: Rand[Int] = _.next

    def unit[A](a: A): Rand[A] = rg => (a, rg)

    def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
      rng => {
        val (a, rng2) = s(rng)
        (f(a), rng2)
      }

    def double: Rand[Double] = {
      map(int)(i => i.toDouble / Int.MaxValue)
    }

    def next: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66CL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRG = RNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRG)
    }
  }

  case class IntRG(seed: Long) extends RG[Int] {
    def next: (Int, RG[Int]) = {
      val newSeed = (seed * 0x5DEECE66CL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRG = IntRG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRG)
    }
  }

  case class DoubleRG(rg: RG[Int]) extends RG[Double] {
    def next: (Double, RG[Double]) = {
      val (value, nextRg) = rg.next
      (value.toDouble / Int.MaxValue, DoubleRG(nextRg))
    }
  }

  case class NonNegativeRG(rg: RG[Int]) extends RG[Int] {
    def next: (Int, NonNegativeRG) = {
      val (value, nextRg) = rg.next
      (Math.abs(value), NonNegativeRG(nextRg))
    }
  }

  case class IntDoubleRG(rg: RG[Int]) extends RG[(Int, Double)] {

    override def next: ((Int, Double), RG[(Int, Double)]) = {
      val (value, nextRg) = rg.next
      val (doubleValue, doubleRG) = DoubleRG(nextRg).next
      ((value.toInt, doubleValue), IntDoubleRG(nextRg.next._2))
    }
  }

  trait RG[A] {
    def next: (A, RG[A])
  }

}
