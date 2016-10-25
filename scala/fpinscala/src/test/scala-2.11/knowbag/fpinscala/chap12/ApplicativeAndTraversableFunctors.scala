package knowbag.fpinscala.chap12

import java.util.Date

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

/**
  * Created by dev-williame on 10/18/16.
  */
class ApplicativeAndTraversableFunctors extends FlatSpec with Matchers {

  "Exercise 12.4" should "understand sequence on stream" in {
    val seq = streamApplicative.sequence(List(Stream.from(1), Stream.from(2)))
    seq.take(10).foreach(println)
  }

  "Exercise 12.5" should "implement either Monad" in {
    eitherMonad.flatMap(Right(10))(a => Right(a + 1)) should be(Right(11))
    eitherMonad[String].flatMap[Int, Int](Left("error"))(a => Right(a + 1)) should be(Left("error"))
  }

  "Exercise 12.6" should "implement Validation applicative" in {
    validationApplicative[String].map2[String, String, String](Failure("test"), Failure("other test"))(_ + _) should be(Failure("test", Vector("other test")))
  }

  "validate web form" should "Return all errors in one failure" in {
    validateWebForm("", "lksdjfkjs", "slkdfjslkdjf") should be(Failure("Name cannot be empty", Vector("Phone number must be 10 digits", "Birthdate must be in the form yyyy-MM-dd")))
  }

  "Exercise 12.8" should "implement applicative product" in {
    val value = streamApplicative.product(streamApplicative).map2[Int, String, String]((Stream.continually(1), Stream.continually(2)), (Stream.continually("1"), Stream.continually("2")))((a, b) => a + b)
    value._1.take(10).foreach(println)
    value._2.take(10).foreach(println)
  }

  "Exercise 12.9" should "implement compose for applicative" in {
    val value = streamApplicative.compose(streamApplicative).map2[Int, Int, Int](Stream.continually(Stream.continually(1)), Stream.continually(Stream.continually(2)))((a, b) => a + b)
    value.take(10).foreach(_.take(10).foreach(println))
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

  trait Applicative[F[_]] extends Functor[F] {
    def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
      apply[B, C](map(fa)(f.curried))(fb)

    def unit[A](a: => A): F[A]

    override def map[A, B](fa: F[A])(f: A => B): F[B] =
      map2(fa, unit(()))((a, _) => f(a))

    def traverse[A, B](as: List[A])(f: A => F[B]): F[List[B]] =
      as.foldRight(unit(List.empty[B]))((a, b) => map2(f(a), b)(_ :: _))

    def sequence[A](fas: List[F[A]]): F[List[A]] =
      fas.foldRight(unit(List.empty[A]))((a, b) => map2(a, b)(_ :: _))

    def replicateM[A](n: Int, fa: F[A]): F[List[A]] =
      sequence(List.fill(n)(fa))

    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
      map2(fa, fb)((_, _))

    def apply[A, B](fab: F[A => B])(fa: F[A]): F[B] =
      map2(fab, fa)((f, a) => f(a))

    def _map[A, B](fa: F[A])(f: A => B): F[B] =
      apply[A, B](unit(f))(fa)

    def _map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = {
      val value: F[B => C] = apply[A, B => C](unit(f.curried))(fa)
      apply(value)(fb)
    }

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

    def product[G[_]](G: Applicative[G]): Applicative[({type f[x] = (F[x], G[x])})#f] = {
      val self = this
      new Applicative[({type f[x] = (F[x], G[x])})#f] {
        override def unit[A](a: => A): (F[A], G[A]) = (self.unit(a), G.unit(a))

        override def apply[A, B](fs: (F[A => B], G[A => B]))(p: (F[A], G[A])) =
          (self.apply(fs._1)(p._1), G.apply(fs._2)(p._2))

        override def map[A, B](fa: (F[A], G[A]))(f: (A) => B): (F[B], G[B]) = _map(fa)(f)
      }
    }

    def compose[G[_]](G: Applicative[G]): Applicative[({type f[x] = F[G[x]]})#f] = {
      val self = this
      new Applicative[({type f[x] = F[G[x]]})#f] {
        def unit[A](a: => A) = self.unit(G.unit(a))

        override def map2[A, B, C](fga: F[G[A]], fgb: F[G[B]])(f: (A, B) => C) =
          self.map2(fga, fgb)(G.map2(_, _)(f))
      }
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


  def eitherMonad[E] = new Monad[({type f[x] = Either[E, x]})#f] {
    override def unit[A](a: A): Either[E, A] = Right(a)

    override def flatMap[A, B](fa: Either[E, A])(f: (A) => Either[E, B]): Either[E, B] =
      fa match {
        case Left(e) => Left(e)
        case Right(a) => f(a)
      }
  }

  val streamApplicative = new Applicative[Stream] {

    override def map2[A, B, C](fa: Stream[A], fb: Stream[B])(f: (A, B) => C): Stream[C] =
      fa.zip(fb).map(f.tupled)

    override def unit[A](a: => A): Stream[A] =
      Stream.continually(a)

  }

  sealed trait Validation[+E, +A]

  case class Failure[E](head: E, tail: Vector[E] = Vector()) extends Validation[E, Nothing]

  case class Success[A](a: A) extends Validation[Nothing, A]


  def validationApplicative[E] = new Applicative[({type f[x] = Validation[E, x]})#f] {
    override def map2[A, B, C](fa: Validation[E, A], fb: Validation[E, B])(f: (A, B) => C): Validation[E, C] = (fa, fb) match {
      case (Success(a), Success(b)) => Success(f(a, b))
      case (Failure(h1, t1), Failure(h2, t2)) => Failure(h1, h2 +: (t1 ++ t2))
      case (Failure(h, t), _) => Failure(h, t)
      case (_, Failure(h, t)) => Failure(h, t)
    }


    override def unit[A](a: => A): Validation[E, A] = Success(a)
  }

  case class WebForm(name: String, birthdate: Date, phoneNumber: String)

  def validName(name: String): Validation[String, String] =
    if (name != "") Success(name)
    else Failure("Name cannot be empty")

  def validBirthdate(birthdate: String): Validation[String, Date] =
    Try {
      import java.text._
      new SimpleDateFormat("yyyy-MM-dd").parse(birthdate)
    } map (Success(_)) getOrElse Failure("Birthdate must be in the form yyyy-MM-dd")

  def validatePhone(phoneNumber: String): Validation[String, String] =
    if (phoneNumber.matches("[0-9]{10}")) Success(phoneNumber)
    else Failure("Phone number must be 10 digits")

  def validateWebForm(name: String, birthdate: String, phone: String): Validation[String, WebForm] =
    validationApplicative.map3(validName(name), validBirthdate(birthdate), validatePhone(phone))(WebForm)

}
