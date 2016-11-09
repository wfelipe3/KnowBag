package com.squirrel

import org.scalatest.{FunSuite, Matchers}

import scalaz._

/**
  * Created by dev-williame on 11/6/16.
  */
class SquirrelActivitiesTest extends FunSuite with Matchers {

  import SquirrelDomain._

  test("When a squirrel is created the position should be x= 0 and y=0") {
    val squirrel = for {
      s <- born("Maria")
      s1 <- moveUp(s, 10)
    } yield s1

    val (events, finalSquirrel) = squirrel(List())

    assert(events == List(InitialState("Maria", 0, 0), MovedUp("Maria", 10)))
    assert(finalSquirrel == Squirrel("Maria", Position(0, 10)))

    assert(squirrel.eval(List()) == Squirrel("Maria", Position(0, 10)))
    assert(squirrel.exec(List()) == List(InitialState("Maria", 0, 0), MovedUp("Maria", 10)))
  }

  sealed trait Event
  case class InitialState(name: String, x: Int, y: Int) extends Event
  case class MovedUp(name: String, spaces: Int) extends Event

  case class Squirrel(name: String, position: Position)
  case class Position(x: Int, y: Int)

  object SquirrelDomain {

    import SquirrelLenses._

    def born(name: String): State[List[Event], Squirrel] =
      State[List[Event], Squirrel] { e =>
        val s = Squirrel(name, Position(0, 0))
        (e :+ InitialState(name, 0, 0), s)
      }

    def moveUp(squirrel: Squirrel, spaces: Int): State[List[Event], Squirrel] =
      State[List[Event], Squirrel] { e =>
        val p = squirrel.position
        val s = squirrelYPositionLens.set(squirrel, p.y + 10)
        (e :+ MovedUp(squirrel.name, spaces), s)
      }
  }

  object SquirrelLenses {
    val squirrelPositionLens = Lens.lensu[Squirrel, Position](
      set = (s, p) => s.copy(position = p),
      get = _.position
    )

    val positionYLens = Lens.lensu[Position, Int](
      set = (p, yValue) => p.copy(y = yValue),
      get = _.y
    )

    val squirrelYPositionLens = squirrelPositionLens >=> positionYLens
  }
}
