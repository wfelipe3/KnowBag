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

  "Exercise 11.5" should "implement replicateM" in {
    Monad.optionMonad.replicateM(2, Some(10)) should be(Some(List(10, 10)))
    Monad.listMonad.replicateM(2, List(1, 2)) should be(List(List(1, 1), List(1, 2), List(2, 1), List(2, 2)))
  }

  "Exercise 11.6" should "implement filterM" in {
    Monad.optionMonad.filterM(List(1, 2, 3, 4, 5))(i => Some(true)) should be(Some(List(1, 2, 3, 4, 5)))
  }

  "Exercise 11.18" should "see monad combinators in state" in {
    println(Monad.stateMonad.replicateM(3, State((s: Map[String, Int]) => (1, s + ("1" -> 1)))).run(Map("0" -> 0)))
  }

  "Exercise 11.20" should "implement reader monad" in {
    val r = Reader.readerMonad[Int]
    r.map2(Reader((r: Int) => r + 1), Reader((r: Int) => r + 2))(_ + _).run(10) should be(23)
    println(r.replicateM(3, Reader((r: Int) => r + 1)).run(10))
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
        map2(v, acc)(_ :: _)
      }

    def traverse[A, B](la: List[A])(f: A => F[B]): F[List[B]] =
      la.foldRight(unit(List[B]())) { (v, acc) =>
        map2(f(v), acc)(_ :: _)
      }

    //Exercise 11.4
    def replicateM[A](n: Int, ma: F[A]): F[List[A]] =
    sequence(List.fill(n)(ma))


    // Exercise 11.7
    def compose[A, B, C](f: A => F[B], g: B => F[C]): A => F[C] =
    a => flatMap(f(a))(g)

    def filterM[A](ms: List[A])(f: A => F[Boolean]): F[List[A]] =
      ms.foldRight(unit(List[A]())) { (v, acc) =>
        compose(f, (b: Boolean) => if (b) map2(unit(v), acc)(_ :: _) else acc)(v)
      }

    // Exercise 11.8
    def _flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] =
    compose((_: Unit) => fa, f)(())

    // Exercise 11.12
    def join[A](mma: F[F[A]]): F[A] =
    flatMap(mma)(ma => ma)

    // Exercise 11.13
    def _compose[A, B, C](f: A => F[B], g: B => F[C]): A => F[C] =
    a => join(map(f(a))(g))

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

    // Exercise 11.17
    val idMonad = new Monad[Id] {
      override def unit[A](a: A): Id[A] = Id(a)

      override def flatMap[A, B](fa: Id[A])(f: (A) => Id[B]): Id[B] = fa.flatMap(f)
    }

    def stateMonad[S] = new Monad[({type f[x] = State[S, x]})#f] {
      override def unit[A](a: A): State[S, A] = State(s => (a, s))

      override def flatMap[A, B](fa: State[S, A])(f: (A) => State[S, B]): State[S, B] = fa.flatMap(f)
    }

  }

  case class Id[A](value: A) {
    def flatMap[B](f: A => Id[B]) = f(value)
  }

  // Exercise 11.20
  case class Reader[R, A](run: R => A)

  object Reader {
    def readerMonad[R] = new Monad[({type f[x] = Reader[R, x]})#f] {
      override def unit[A](a: A): Reader[R, A] = Reader(_ => a)

      override def flatMap[A, B](fa: Reader[R, A])(f: (A) => Reader[R, B]): Reader[R, B] = Reader(r => f(fa.run(r)).run(r))
    }
  }

  val listFunctor = new Functor[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] =
      fa.map(f)
  }

  case class State[S, +A](run: S => (A, S)) {

    import State._

    def map[B](f: A => B): State[S, B] = {
      flatMap(a => unit(f(a)))
    }

    def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] = {
      flatMap(a => sb.map(b => f(a, b)))
    }

    def flatMap[B](f: A => State[S, B]): State[S, B] = {
      State(s => {
        val (a, newS) = run(s)
        f(a).run(newS)
      })
    }

  }

  object State {
    def unit[S, A](a: A): State[S, A] =
      State(s => (a, s))

    def sequence[S, A](l: List[State[S, A]]): State[S, List[A]] =
      l.foldRight(unit[S, List[A]](List.empty)) { (as, is) =>
        as.map2(is)(_ :: _)
      }

    def get[S]: State[S, S] = State(s => (s, s))

    def set[S](s: S): State[S, Unit] = State(_ => ((), s))

    def modify[S](f: S => S): State[S, Unit] = for {
      s <- get
      _ <- set(f(s))
    } yield ()
  }

}
