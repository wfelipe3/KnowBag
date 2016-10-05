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

  "Exercise 10.7" should "implement foldMapV" in {
    foldMapV(IndexedSeq(1, 2, 3, 4, 5), stringMonoid)(_.toString) should be("12345")
  }

  "Exercise 10.11" should "implement count words" in {
    countWords("this is a test") should be(4)
  }

  "Exercise 10.13" should "implement foldable tree" in {
    FoldableTree.foldLeft(
      Branch(
        Leaf(1),
        Leaf(2)
      )
    )(0)(_ + _) should be(3)
  }

  "Exercise 10.16" should "implement product composed monoid" in {
    val m = productMonoid(intAddMonoid, booleanAndMonoid)
    FoldableList.foldMap(List((1, true), (2, false), (3, true)))(a => a)(m) should be((6, false))
  }

  "mapMergeMonoid" should "compose to bigger monoids" in {
    val m: Monoid[Map[String, Map[String, Int]]] = mapMergeMonoid(mapMergeMonoid(intAddMonoid))
    val m1 = Map("o1" -> Map("i1" -> 1, "i2" -> 2))
    val m2 = Map("o1" -> Map("i2" -> 3))
    m.op(m1, m2) should be(Map("o1" -> Map("i1" -> 1, "i2" -> 5)))
  }

  "Exercise 10.17" should "implement function monoid" in {
    val toInt = (a: String) => a.toInt
    val toIntPlusTen = (a: String) => a.toInt + 10
    val fSumM = functionMonoid[String, Int](intAddMonoid)
    val other = fSumM.op(toInt, toIntPlusTen)
    other("10") should be(10 + 10 + 10)
  }

  sealed trait WC

  case class Stub(chars: String) extends WC

  case class Part(lStub: String, words: Int, rStub: String) extends WC

  //Exercise 10.10
  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    override def zero: WC = Stub("")

    override def op(a1: WC, a2: WC): WC = (a1, a2) match {
      case (Stub(v1), Stub(v2)) =>
        Stub(v1 + v2)
      case (Stub(v1), Part(l1, c1, r1)) =>
        Part(v1 + l1, c1, r1)
      case (Part(l1, c1, r1), Stub(v1)) =>
        Part(l1, c1, v1 + r1)
      case (Part(l1, c1, r1), Part(l2, c2, r2)) =>
        Part(l1, c1 + (if ((r1 + l2).isEmpty) 0 else 1) + c2, r2)
    }
  }

  def countWords(words: String): Int = {
    def wc(c: Char): WC =
      if (c.isWhitespace)
        Part("", 0, "")
      else
        Stub(c.toString)
    def unstub(s: String) = s.length min 1
    foldMapV(words.toIndexedSeq, wcMonoid)(wc) match {
      case Stub(c) => unstub(c)
      case Part(l, w, r) => unstub(l) + w + unstub(r)
    }
  }

  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B =
    as.foldRight(m.zero)((v, a) => m.op(f(v), a))


  def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(as, endoMonoid[B])(f.curried)(z)


  def foldMapV[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = {
    if (v.size == 1)
      f(v.head)
    else if (v.isEmpty)
      m.zero
    else {
      val (left, right) = v.splitAt(v.size / 2)
      m.op(foldMapV(left, m)(f), foldMapV(right, m)(f))
    }
  }


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

  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    override def zero: A = m.zero

    override def op(a1: A, a2: A): A = m.op(a2, a1)
  }

  def productMonoid[A, B](a: Monoid[A], b: Monoid[B]): Monoid[(A, B)] = new Monoid[(A, B)] {
    override def zero: (A, B) = (a.zero, b.zero)

    override def op(a1: (A, B), a2: (A, B)): (A, B) = (a1, a2) match {
      case ((a1, b1), (a2, b2)) => (a.op(a1, a2), b.op(b1, b2))
    }
  }

  def mapMergeMonoid[K, V](V: Monoid[V]): Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
    override def zero: Map[K, V] = Map.empty

    override def op(a1: Map[K, V], a2: Map[K, V]): Map[K, V] = (a1.keySet ++ a2.keySet).foldLeft(zero) { (acc, k) =>
      acc.updated(k, V.op(a1.getOrElse(k, V.zero), a2.getOrElse(k, V.zero)))
    }
  }

  def functionMonoid[A, B](B: Monoid[B]): Monoid[A => B] = new Monoid[A => B] {
    override def zero: (A) => B = a => B.zero

    override def op(a1: (A) => B, a2: (A) => B): (A) => B = a => B.op(a1(a), a2(a))
  }


  //Exercise 10.12
  trait Foldable[F[_]] {
    def foldRight[A, B](as: F[A])(z: B)(f: (A, B) => B): B =
      foldMap(as)(f.curried)(endoMonoid[B])(z)

    def foldLeft[A, B](as: F[A])(z: B)(f: (B, A) => B): B =
      foldMap(as)(a => (b: B) => f(b, a))(dual(endoMonoid))(z)

    def foldMap[A, B](as: F[A])(f: A => B)(mb: Monoid[B]): B =
      foldRight(as)(mb.zero)((v, l) => mb.op(f(v), l))

    def concatenate[A](as: F[A])(m: Monoid[A]): A =
      foldLeft(as)(m.zero)(m.op)

    //Exercise 10.15
    def toList[A](fa: F[A]): List[A] =
    foldRight(fa)(List.empty[A])(_ :: _)
  }

  object FoldableList extends Foldable[List] {
    override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B =
      as.foldRight(z)(f)

    override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B =
      as.foldLeft(z)(f)

    override def foldMap[A, B](as: List[A])(f: (A) => B)(mb: Monoid[B]): B =
      as.foldRight(mb.zero)((a, b) => mb.op(f(a), b))
  }

  // Exercise 10.14
  object FoldableOption extends Foldable[Option] {
    override def foldLeft[A, B](as: Option[A])(z: B)(f: (B, A) => B): B =
      as.map(v => f(z, v)).getOrElse(z)

    override def foldRight[A, B](as: Option[A])(z: B)(f: (A, B) => B): B =
      as.map(v => f(v, z)).getOrElse(z)

    override def foldMap[A, B](as: Option[A])(f: (A) => B)(mb: Monoid[B]): B =
      as.map(f).getOrElse(mb.zero)
  }

  // Exercise 10.13
  object FoldableTree extends Foldable[Tree] {
    override def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) => B): B = as match {
      case Leaf(v) => f(z, v)
      case Branch(l, r) => foldLeft(r)(foldLeft(l)(z)(f))(f)
    }

    override def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) => B): B = as match {
      case Leaf(v) => f(v, z)
      case Branch(l, r) => foldRight(l)(foldRight(r)(z)(f))(f)
    }

    override def foldMap[A, B](as: Tree[A])(f: (A) => B)(mb: Monoid[B]): B = as match {
      case Leaf(v) => f(v)
      case Branch(l, r) => mb.op(foldMap(l)(f)(mb), foldMap(r)(f)(mb))
    }
  }

  sealed trait Tree[+A]

  case class Leaf[A](value: A) extends Tree[A]

  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

}
