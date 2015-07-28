package knowbag.functional.state

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/20/15.
 */
class CandyDispenserTest extends FlatSpec with Matchers {

  "turn knob on a locked machine" should "return initial state" in {
    simulateMachine(List(Turn)).map {
      case (coins, candies) =>
        coins should be(0)
        candies should be(10)
    }.run(Machine(locked = true, candies = 10, coins = 0))
  }

  "insert coin in unlocked machine" should "return initial state" in {
    val machine = simulateMachine(List(Coin)).map {
      case (coins, candies) =>
        coins should be(1)
        candies should be(9)
    }.run(Machine(locked = false, candies = 9, coins = 1))
  }

  "insert coin without candies" should "ignore input" in {
    val machine = simulateMachine(List(Coin)).map {
      case (coins, candies) =>
        coins should be(10)
        candies should be(0)
    }

    machine.run(Machine(locked = true, candies = 0, coins = 10))
    machine.run(Machine(locked = false, candies = 0, coins = 10))
  }

  "turn knob in machine without candies" should "ignore input" in {
    simulateMachine(List(Turn)).map {
      case (coins, candies) => {
        coins should be(11)
        candies should be(0)
      }
    }.run(Machine(locked = false, candies = 0, coins = 11))
  }

  "insert coin in locked machine" should "unlock machine" in {
    simulateMachine(List(Coin)).map {
      case (coins, candies) =>
        coins should be(12)
        candies should be(10)
    }.run(Machine(locked = true, candies = 10, coins = 11))
  }

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = for {
    _ <- (State.modify[Machine] _ compose update)(inputs.head)
    s <- State.get
  } yield (s.coins, s.candies)

  def update = (i: Input) => (m: Machine) =>
    (i, m) match {
      case (Turn, Machine(true, _, _)) => m
      case (Coin, Machine(false, _, _)) => m
      case (_, Machine(_, 0, _)) => m
      case (Coin, Machine(true, candies, coins)) => Machine(locked = false, candies, coins + 1)
    }

  sealed trait Input

  case object Turn extends Input

  case object Coin extends Input

  case class Machine(locked: Boolean, candies: Int, coins: Int)

}
