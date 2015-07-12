package knowbag.functional.example

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/18/15.
 */
class ToFunctional extends FlatSpec with Matchers {

  behavior of "passing to functional"

  it should "The problem with the function buy coffee is that is charging to the credit, if the functions is " +
    "seen out side it is not obvious that a that a charge is being made to the credit card, this makes difficult " +
    "to test this kind of functions, it has side effects" in {
    class Cafe {
      def buyCoffee(cc: CreditCard): Coffee = {
        val cup = new Coffee()
        cc.charge(cup.price)
        cup
      }
    }

    class CreditCard {
      def charge(price: Int) = ()
    }

    class Coffee {
      def price = 10
    }
  }

  it should "In a refactor where the credit card is hard to test, and is obvious that the credit card should not charge for the buy" in {
    class Cafe {
      def buyCoffee(cc: CreditCard, p: Payments): Coffee = {
        val cup = new Coffee()
        p.charge(cc, cup.price)
        cup
      }
    }

    class CreditCard {
    }

    class Coffee {
      def price = 10
    }

    class Payments {
      def charge(cc: CreditCard, price: Int) = ()
    }
  }

  it should "The functional way" in {

    case class Charge(cc: CreditCard, price: Int) {
      def combine(other: Charge): Charge = {
        if (cc == other)
          Charge(cc, price + other.price)
        else
          throw new Exception("message")
      }
    }

    def coalesce(charges: List[Charge]): List[Charge] = {
      charges.groupBy(_.cc).values.map(_.reduce(_ combine _)).toList
    }

    class Cafe {
      def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
        val cup = new Coffee()
        (cup, Charge(cc, cup.price))
      }

      def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
        val purchases: List[(Coffee, Charge)] = List.fill(n)(buyCoffee(cc))
        val (coffees, charges) = purchases.unzip
        (coffees, charges.reduce((c1, c2) => c1.combine(c2)))
      }
    }

    class CreditCard {
    }

    class Coffee {
      def price = 10
    }
  }
}
