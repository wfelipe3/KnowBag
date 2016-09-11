package knowbag.fpinscala.chap6

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by feliperojas on 8/20/16.
  */
class PurelyFunctionalState extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  behavior of "RNG"

  it should "return same value for same seed" in {
    val random = new SimpleRNG(42)
    val (n, nextRandom) = random.nextInt
    n should be(random.nextInt._1)
    nextRandom.nextInt._1 should not be n

  }

  "Exercise 6.1" should "return non negative random number" in {
    forAll(Gen.negNum[Int]) { (x: Int) =>
      nonNegativeInt(new SimpleRNG(x))._1 should be >= 0
    }
  }

  "Exercise 6.2" should "implement random double" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val value = double(new SimpleRNG(x))
      betweenZeroAndOne(value._1)
    }
  }

  behavior of "Exercise 6.3"

  it should "implement intDouble function" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val ((int, double), _) = intDouble(new SimpleRNG(x))
      int should be < Int.MaxValue
      betweenZeroAndOne(double)
    }
  }

  it should "implement doubleInt function" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val ((double, int), _) = doubleInt(new SimpleRNG(x))
      int should be < Int.MaxValue
      betweenZeroAndOne(double)
    }
  }

  "Exercise 6.4" should "implement list of random ints" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val (values, _) = ints(x)(new SimpleRNG(x))
      values.size should be(x)
      val distribution = values.foldLeft(Map.empty[Int, Int]) { (m, v) =>
        if (m.contains(v))
          m + (v -> (m(v) + 1))
        else
          m + (v -> 1)
      }.forall(_._2 <= 1)
      distribution should be(true)
    }
  }

  "Exercise 6.5" should "implement random double with map" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val value = doubleNew(new SimpleRNG(x))
      betweenZeroAndOne(value._1)
    }
  }

  behavior of "Exerice 6.6 implement map2"

  it should "implement intDouble function with map2" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val ((int, double), _) = intDoubleRand(new SimpleRNG(x))
      int should be < Int.MaxValue
      betweenZeroAndOne(double)
    }
  }

  it should "implement doubleInt function with matp2" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val ((double, int), _) = doubleIntRand(new SimpleRNG(x))
      int should be < Int.MaxValue
      betweenZeroAndOne(double)
    }
  }

  "Exercise 6.7" should "implement sequence" in {
    forAll(Gen.posNum[Int]) { (x: Int) =>
      val (values, _) = intsRand(x)(new SimpleRNG(x))
      values.size should be(x)
      val distribution = values.foldLeft(Map.empty[Int, Int]) { (m, v) =>
        if (m.contains(v))
          m + (v -> (m(v) + 1))
        else
          m + (v -> 1)
      }.forall(_._2 <= 1)
      distribution should be(true)
    }
  }

  def betweenZeroAndOne(actual: Double): Unit = {
    actual should be > 0.0
    actual should be < 1.0
  }


  trait RNG {
    def nextInt: (Int, RNG)
  }

  class SimpleRNG(seed: Long) extends RNG {

    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val newRNG = new SimpleRNG(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, newRNG)
    }
  }

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (v, nextRng) = rng.nextInt
    (Math.abs(v), nextRng)
  }

  def double(rng: RNG): (Double, RNG) = {
    val (i, next) = rng.nextInt
    (Math.abs(i / (Int.MaxValue.toDouble + 1)), next)
  }

  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (i, next) = rng.nextInt
    val (d, nextD) = double(next)
    ((i, d), nextD)
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val (d, nextD) = double(rng)
    val (i, next) = nextD.nextInt
    ((d, i), next)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    def ints(rng: RNG)(values: List[Int]): (List[Int], RNG) = {
      if (values.size >= count)
        (values, rng)
      else {
        val next = rng.nextInt
        ints(next._2)(next._1 +: values)
      }
    }
    ints(rng)(List.empty)
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] =
    rng => {
      val (a, n1) = ra(rng)
      val (b, n2) = rb(n1)
      (f(a, b), n2)
    }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] =
    fs.foldRight(unit(List.empty[A])) { (v, r) =>
      map2(v, r)(_ +: _)
    }


  def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = map2(ra, rb)((_, _))

  def nonNegativeEven: Rand[Int] = map(nonNegativeInt)(i => i - i % 2)

  def doubleNew: Rand[Double] = map(nonNegativeInt)(i => Math.abs(i / (Int.MaxValue.toDouble + 1)))

  def intDoubleRand: Rand[(Int, Double)] = both(int, doubleNew)

  def doubleIntRand: Rand[(Double, Int)] = both(doubleNew, int)

  def intsRand(count: Int): Rand[List[Int]] = sequence(List.fill(count)(int))

}