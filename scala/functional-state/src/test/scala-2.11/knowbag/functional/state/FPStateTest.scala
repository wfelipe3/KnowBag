package knowbag.functional.state

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/19/15.
 */
class FPStateTest extends FlatSpec with Matchers {

  "random generator" should "generate a new value with a new state" in {
    val (value, next) = RNG.Simple(10).nextInt
    value shouldBe a[java.lang.Integer]
  }

  "non negative" should "return a non negative random value with its generator" in {
    assert(10) { x =>
      val (value, next) = nonNegative(RNG.Simple(x))
      value should be > 0
    }
  }

  "double" should "return a random double value " in {
    assert(10) { x =>
      val (value, next) = double(RNG.Simple(x))
      value.toInt should (be >= 0 and be < 1)
    }
  }

  "boolean" should "return a boolean value" in {
    val (value, next) = boolean(RNG.Simple(10))
    value shouldBe a[java.lang.Boolean]
  }

  "intDouble" should "return an int and a double with the next generator" in {
    val ((intValue, doubleValue), next) = intDouble(RNG.Simple(10))
    intValue should be(3847489)
    doubleValue should be(1.6213264384406276)
  }

  "ints" should "return a list of random int values" in {
    val (values, next) = ints(10)(RNG.Simple(10))
    println(values)
  }

  "map" should "map a random value with the given function" in {
    val stringRandom = map(unit(10))(_.toString)
    stringRandom(RNG.Simple(10)) match {
      case (value, _) => value should be("10")
    }
  }

  "_double" should "return a double random value" in {
    assert(10) { x =>
      val (value, next) = _double(RNG.Simple(10))
      value.toInt should (be >= 0 and be < 1)
    }
  }

  "_ints" should "return a list of random values" in {
    println(_ints(10)(RNG.Simple(10)))
  }

  def assert(count: Int)(assert: Int => Unit): Unit = {
    Stream.from(Int.MinValue).take(count).foreach(assert)
  }

  trait RNG {
    def nextInt: (Int, RNG)
  }

  object RNG {

    case class Simple(seed: Long) extends RNG {
      override def nextInt: (Int, RNG) = {
        val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
        val nextRNG = Simple(newSeed)
        val n = (newSeed >>> 16).toInt
        (n, nextRNG)
      }
    }

  }

  def nonNegative(rng: RNG): (Int, RNG) = {
    val (value, next) = rng.nextInt
    (Math.abs(value), next)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (value, next) = rng.nextInt
    (value / Int.MaxValue.toDouble + 1, next)
  }

  def boolean(rng: RNG): (Boolean, RNG) = {
    val (value, next) = rng.nextInt
    (value % 2 == 0, next)
  }

  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (intV, nextI) = rng.nextInt
    val (doubleV, nextD) = double(nextI)
    ((intV, doubleV), nextD)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    if (count == 0) (Nil, rng)
    else {
      val (value, next) = rng.nextInt
      val (values, nextI) = ints(count - 1)(next)
      (value :: values, nextI)
    }
  }

  def ints2(count: Int)(rg: RNG): (List[Int], RNG) = {
    def go(actual: Int, values: List[Int])(rg: RNG): (List[Int], RNG) = {
      if (actual == count) (values, rg)
      else go(actual + 1, rg.nextInt._1 :: values)(rg.nextInt._2)
    }
    go(0, Nil)(rg)
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit(a: Int): Rand[Int] = rng => (a, rng)

  def unit2[A](a: A): Rand[A] = rng => (a, rng)

  def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (value, next) = s(rng)
      (f(value), next)
    }

  def map2[A, B, C](r1: Rand[A], r2: Rand[B])(f: (A, B) => C): Rand[C] =
    rng => {
      val (v1, rng1) = r1(rng)
      val (v2, rng2) = r2(rng1)
      (f(v1, v2), rng2)
    }

  def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = map2(ra, rb)((_, _))

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = {
    rng => {
      fs.foldRight((List[A](), rng)) { (rand, values) =>
        val (xs, rn) = values
        val (value, next) = rand(rn)
        (value :: xs, next)
      }
    }
  }

  def sequence2[A](fs: List[Rand[A]]): Rand[List[A]] = {
    val emptyListRandom = unit2(List[A]())
    fs.foldRight(emptyListRandom)((currentRandom, acc) => map2(currentRandom, acc)(_ :: _))
  }

  def flatMap[A, B](f: Rand[A])(b: A => Rand[B]): Rand[B] =
    rng => {
      val (aValue, next) = f(rng)
      b(aValue)(next)
    }

  def _int: Rand[Int] =
    rng => {
      rng.nextInt
    }

  def _intDouble: Rand[(Int, Double)] = both(nonNegative, double)

  def _double: Rand[Double] = {
    map(nonNegative)(_.toDouble / Int.MaxValue)
  }

  def _ints(count: Int): Rand[List[Int]] = sequence(List.fill(count)(_int))

}
