package knowbag.fpinscala.chap10

import org.scalacheck.Gen
import org.scalacheck.Gen.Choose
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/**
  * Created by dev-williame on 9/22/16.
  */
class Monoids extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  behavior of "Monoid"

  "Exercise 10.1" should "implement monoid for different operators" in {
    val intNumGen: Gen[Int] = Gen.chooseNum(Int.MinValue, Int.MaxValue)
    val booleanNumGen: Gen[Boolean] = Gen.choose(true, false)(new Choose[Boolean] {
      override def choose(min: Boolean, max: Boolean): Gen[Boolean] = Random.nextInt() % 2 == 0
    })

    List(
      MonoidLaws(stringMonoid, Gen.numStr),
      MonoidLaws(intAddMonoid, intNumGen),
      MonoidLaws(intMultiplicationMonoid, intNumGen),
      MonoidLaws(booleanOrMonoid, booleanNumGen),
      MonoidLaws(booleanAndMonoid, booleanNumGen)
    ).foreach(m => monoidLaws(m))

    forAll { (x: String, y: String) =>
      stringMonoid.op(x, y) should be(x + y)
    }
  }

  "Exercise 10.2" should "implement monoid for option values" in {
    val optionGen: Gen[Option[String]] = Gen.option(Gen.numStr)
    forAll(optionGen, optionGen, optionGen) { (x, y, z) =>
      testMonoidLaws(optionM[String], x, y, z)
    }
  }

  "Exercise 10.3" should "implement monoid for endofunctors" in {
    endoMonoid[Int].op(endoMonoid.zero, a => a + a)(10) should be(20)
  }

  "Exercise 10.5" should "implement foldMap" in {
    foldMap(List(1, 2, 3, 4, 5), stringMonoid)(_.toString) should be("12345")
  }

  "Exercise 10.6" should "implement foldRight and foldLeft with foldMap" in {
    foldRight(List(1, 2, 3, 4, 5))(intAddMonoid.zero)(intAddMonoid.op) should be(1 + 2 + 3 + 4 + 5)
  }

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B =
    as.foldRight(m.zero)((v, a) => m.op(f(v), a))


  def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(as, endoMonoid[B])(f.curried)(z)


  //Exercise 10.4
  case class MonoidLaws[A](m: Monoid[A], gen: Gen[A])

  private def monoidLaws[A](m: MonoidLaws[A]) = {
    forAll(m.gen, m.gen, m.gen) { (x: A, y: A, z: A) =>
      testMonoidLaws(m.m, x, y, z)
    }
  }

  private def testMonoidLaws[A](m: Monoid[A], a1: A, a2: A, a3: A) = {
    testIdentity(m, a1)
    testAssociativity(m, a1, a2, a3)
  }

  private def testIdentity[A](m: Monoid[A], a: A): Unit = {
    m.op(m.zero, a) should be(m.op(a, m.zero))
  }

  private def testAssociativity[A](m: Monoid[A], a1: A, a2: A, a3: A) = {
    m.op(m.op(a1, a2), a3) should be(m.op(a1, m.op(a2, a3)))
  }

  trait Monoid[A] {
    def zero: A

    def op(a1: A, a2: A): A
  }

  val stringMonoid = new Monoid[String] {
    override def zero: String = ""

    override def op(a1: String, a2: String): String = a1 + a2
  }

  def listMonoid[A] = new Monoid[List[A]] {
    override def zero: List[A] = Nil

    override def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2
  }

  val intAddMonoid = new Monoid[Int] {
    override def zero: Int = 0

    override def op(a1: Int, a2: Int): Int = a1 + a2
  }

  val intMultiplicationMonoid = new Monoid[Int] {
    override def zero: Int = 1

    override def op(a1: Int, a2: Int): Int = a1 * a2
  }

  val booleanOrMonoid = new Monoid[Boolean] {
    override def zero: Boolean = false

    override def op(a1: Boolean, a2: Boolean): Boolean = a1 || a2
  }

  val booleanAndMonoid = new Monoid[Boolean] {
    override def zero: Boolean = true

    override def op(a1: Boolean, a2: Boolean): Boolean = a1 && a2
  }

  def optionM[A] = new Monoid[Option[A]] {
    override def zero: Option[A] = None

    override def op(a1: Option[A], a2: Option[A]): Option[A] = a1 orElse a2
  }

  def endoMonoid[A] = new Monoid[A => A] {
    override def zero: (A) => A = a => a

    override def op(a1: (A) => A, a2: (A) => A): (A) => A = a1.compose(a2)
  }

}
