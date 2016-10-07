package knowbag.fpinscala.chap11

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 10/7/16.
  */
class Monads extends FlatSpec with Matchers {

  "Exercise 11.3" should "implement sequence and traverse for monads" in {
    Monad.optionMonad.sequence(List(Some(1), Some(2), Some(3))) should be(Some(List(1, 2, 3)))
    Monad.optionMonad.sequence(List(Some(1), Some(2), None)) should be(None)
    Monad.optionMonad.traverse[Int, String](List(1, 2, 3))(i => Option(i.toString)) should be(Some(List("1", "2", "3")))
    Monad.optionMonad.traverse[Int, String](List(1, 2, 3))(i => if (i > 2) None else Option(i.toString)) should be(None)
  }

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]

    def distribute[A, B](fab: F[(A, B)]): (F[A], F[B]) =
      (map(fab)(_._1), map(fab)(_._2))

    def codistibute[A, B](e: Either[F[A], F[B]]): F[Either[A, B]] =
      e match {
        case Left(fa) => map(fa)(Left(_))
        case Right(fb) => map(fb)(Right(_))
      }
  }

  trait Monad[F[_]] extends Functor[F] {

    def unit[A](a: A): F[A]

    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

    def map[A, B](fa: F[A])(f: A => B): F[B] =
      flatMap(fa)(a => unit(f(a)))

    def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
      flatMap(fa)(a => map(fb)(b => f(a, b)))

    def sequence[A](lma: List[F[A]]): F[List[A]] =
      lma.foldRight(unit(List[A]())) { (v, acc) =>
        flatMap(v)(a => map(acc)(l => a :: l))
      }

    def traverse[A, B](la: List[A])(f: A => F[B]): F[List[B]] =
      la.foldRight(unit(List[B]())) { (v, acc) =>
        flatMap(f(v))(a => map(acc)(l => a :: l))
      }
  }

  // Exercise 11.1
  object Monad {
    val listMonad = new Monad[List] {
      override def unit[A](a: A): List[A] = List(a)

      override def flatMap[A, B](fa: List[A])(f: (A) => List[B]): List[B] =
        fa.flatMap(f)
    }

    val optionMonad = new Monad[Option] {
      override def unit[A](a: A): Option[A] = Option(a)

      override def flatMap[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] =
        fa.flatMap(f)
    }
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] =
      fa.map(f)
  }

}
