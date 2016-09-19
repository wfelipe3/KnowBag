package knowbag.fpinscala.chap6

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 9/8/16.
  */
class State extends FlatSpec with Matchers {

  behavior of "Exercise 6.10 implement map combinator"

  it should "implement map in state" in {
    val s = State[Map[Int, String], Int](s => {
      (2, s + (2 -> "two"))
    }).map(_.toString)

    s.run(Map(1 -> "one")) should be(("2", Map(1 -> "one", 2 -> "two")))
  }

  it should "implement flatMap in state" in {
    val s = State[Map[Int, String], Int](s => {
      (2, s + (2 -> "two"))
    }).flatMap(a => State(s => (a.toString, s + (3 -> "three"))))

    s.run(Map(1 -> "one")) should be(("2", Map(1 -> "one", 2 -> "two", 3 -> "three")))

    val state = for {
      state <- State[Map[Int, String], Int](s => (1, s + (1 -> "one")))
      newState <- State[Map[Int, String], Int](s => (2, s + (2 -> "two")))
      thirdState <- State[Map[Int, String], Int](s => (3, s + (3 -> "three")))
    } yield thirdState

    state.run(Map.empty) should be((3, Map(1 -> "one", 2 -> "two", 3 -> "three")))
  }

  it should "implement map2 in state" in {
    val s = State[Map[Int, String], Int](s => {
      (1, s + (1 -> "one"))
    })

    val s1 = State[Map[Int, String], Int](s => {
      (2, s + (2 -> "two"))
    })

    s.map2(s1)(_ + _).run(Map.empty) should be(3, Map(1 -> "one", 2 -> "two"))
  }

  it should "implement sequence" in {
    val s = State[Map[Int, String], Int](s => {
      (1, s + (1 -> "one"))
    })

    val s1 = State[Map[Int, String], Int](s => {
      (2, s + (2 -> "two"))
    })

    State.sequence(List(s, s1)).run(Map.empty) should be(List(1, 2), Map(1 -> "one", 2 -> "two"))
  }

  "Exercise 6.11" should "implement candy dispenser" in {
      simulateMachine(List(Coin, Turn)).run(Machine(locked = true, 10, 10)) should be(((9, 11), Machine(true, 9, 11)))
  }

  import State._

  sealed trait Input

  case object Coin extends Input

  case object Turn extends Input

  case class Machine(locked: Boolean, candies: Int, coins: Int)

  object Candy {
    def update = (i: Input) => (s: Machine) =>
      (i, s) match {
        case (_, Machine(_, 0, _)) => s
        case (Coin, Machine(false, _, _)) => s
        case (Turn, Machine(true, _, _)) => s
        case (Coin, Machine(true, candy, coin)) =>
          Machine(locked = false, candy, coin + 1)
        case (Turn, Machine(false, candy, coin)) =>
          Machine(locked = true, candy - 1, coin)
      }
  }

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] =
    for {
      _ <- State.sequence(inputs.map(modify[Machine] _ compose Candy.update))
      s <- get
    } yield (s.candies, s.coins)

  case class State[S, +A](run: S => (A, S)) {

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
