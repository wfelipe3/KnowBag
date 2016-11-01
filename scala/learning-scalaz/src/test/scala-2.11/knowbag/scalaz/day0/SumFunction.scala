package knowbag.scalaz.day0

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 11/1/16.
  */
class SumFunction extends FlatSpec with Matchers {

  behavior of "sum function"

  it should "sum values in a list" in {
    def sum(xs: List[Int]): Int = xs.foldLeft(0)(_ + _)
    sum(List(1, 2, 4)) should be(1 + 2 + 4)
  }

  it should "sum with monoid too" in {
    object IntMonoid {
      def mappend(a: Int, b: Int) = a + b
      def mzero: Int = 0
    }

    def sum(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)

    sum(List(1, 2, 4)) should be(1 + 2 + 4)
  }

  it should "sum with a more general monoid" in {
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

    val multMonoid = new Monoid[Int] {
      override def mappend(a: Int, b: Int): Int = a * b
      override def mzero: Int = 1
    }

    def sum[A: Monoid](xs: List[A]): A = {
      val m = implicitly[Monoid[A]]
      xs.foldLeft(m.mzero)(m.mappend)
    }

    sum(List(1, 2, 4)) should be(1 + 2 + 4)
    sum(List("a", "b", "c")) should be("abc")
    sum(List(1, 2, 4))(multMonoid) should be(1 * 2 * 4)
  }
}
