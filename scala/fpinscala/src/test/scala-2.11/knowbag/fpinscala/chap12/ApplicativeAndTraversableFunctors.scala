package knowbag.fpinscala.chap12

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 10/18/16.
  */
class ApplicativeAndTraversableFunctors extends FlatSpec with Matchers {

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

  trait Applicative[F[_]] extends Functor[F] {
    def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C]

    def unit[A](a: => A): F[A]

    def map[A, B](fa: F[A])(f: A => B): F[B] =
      map2(fa, unit(()))((a, _) => f(a))

    def traverse[A, B](as: List[A])(f: A => F[B]): F[List[B]] =
      as.foldRight(unit(List.empty[B]))((a, b) => map2(f(a), b)(_ :: _))

    //Exercise 12.1
    def sequence[A](fas: List[F[A]]): F[List[A]] =
    fas.foldRight(unit(List.empty[A]))((a, b) => map2(a, b)(_ :: _))

    def replicateM[A](n: Int, fa: F[A]): F[List[A]] =
      sequence(List.fill(n)(fa))

    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
      map2(fa, fb)((_, _))

    //Exercise 12.2
    def apply[A, B](fab: F[A => B])(fa: F[A]): F[B] =
    map2(fab, fa)((f, a) => f(a))

    def _map[A, B](fa: F[A])(f: A => B): F[B] =
      apply[A, B](unit(f))(fa)

    def _map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = {
      val value: F[B => C] = apply[A, B => C](unit(f.curried))(fa)
      apply(value)(fb)
    }

    //Exercise 12.3
    def map3[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = {
      val fbcd: F[B => C => D] = apply[A, B => C => D](unit(f.curried))(fa)
      val fcd = apply(fbcd)(fb)
      apply(fcd)(fc)
    }

    def map4[A, B, C, D, E](fa: F[A], fb: F[B], fc: F[C], fd: F[D])(f: (A, B, C, D) => E): F[E] = {
      val fbcde: F[B => C => D => E] = apply[A, B => C => D => E](unit(f.curried))(fa)
      val fcde = apply(fbcde)(fb)
      val fde = apply(fcde)(fc)
      apply(fde)(fd)
    }

  }

}
