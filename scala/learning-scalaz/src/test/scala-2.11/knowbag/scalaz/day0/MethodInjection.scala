package knowbag.scalaz.day0

import org.scalatest.{FlatSpec, Matchers}
import scalaz._
import Scalaz._

/**
  * Created by dev-williame on 11/1/16.
  */
class MethodInjection extends FlatSpec with Matchers {

  trait Monoid[A] {
    def mappend(a: A, b: A): A
    def mzero: A
  }
  object Monoid {
    implicit val intMonoid = new Monoid[Int] {
      def mappend(a: Int, b: Int) = a + b
      def mzero: Int = 0
    }
    implicit val stringMonoid = new Monoid[String] {
      def mappend(a: String, b: String) = a + b
      def mzero: String = ""
    }
  }

  "monoid operator" should "add an aperator to a given monoid type" in {
    trait MonoidOp[A] {
      val F: Monoid[A]
      val value: A
      def |+|(a2: A) = F.mappend(value, a2)
    }

    implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
      override val value: A = a
      override val F: Monoid[A] = implicitly[Monoid[A]]
    }

    3 |+| 4 should be(7)
    "a" |+| "b" should be("ab")

    1.some | 2 should be(1)
    Some(1).getOrElse(2) should be(1)

    (1 > 10) ? 1 | 2 should be(2)
  }
}
