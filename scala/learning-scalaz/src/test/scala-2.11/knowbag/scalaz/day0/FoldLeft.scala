package knowbag.scalaz.day0

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 11/1/16.
  */
class FoldLeft extends FlatSpec with Matchers {

  behavior of "foldleft"

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

  it should "be possible to create a foldleft object type class" in {
    object FoldLeftList {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
    }

    def sum[A: Monoid](xs: List[A]): A = {
      val m = implicitly[Monoid[A]]
      FoldLeftList.foldLeft(xs, m.mzero, m.mappend)
    }

    sum(List(1, 2, 4)) should be(1 + 2 + 4)
  }

  it should "be possible to generalize fold left" in {
    trait FoldLeft[F[_]] {
      def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
    }
    object FoldLeft {
      implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
        override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
      }
    }

    def sum[M[_] : FoldLeft, A: Monoid](xs: M[A]): A = {
      val m = implicitly[Monoid[A]]
      val fl = implicitly[FoldLeft[M]]
      fl.foldLeft(xs, m.mzero, m.mappend)
    }


    sum(List(1, 2, 4)) should be(1 + 2 + 4)
    sum(List("a", "b", "c")) should be("abc")
  }
}
