package com.bizagi.fp

import org.scalatest.{FreeSpec, Matchers}

/**
  * Created by dev-williame on 1/18/17.
  */
class CoffeeShopFPExample extends FreeSpec with Matchers {

  "Coffee shop version 1.0 with side effect functions" - {
    case class CreditCard(number: String, expDate: String, csv: Int) {
      def charge(amount: Int): Unit = println(s"charging $amount to credit card $number")
    }

    case class Coffee(price: Int = 2000)

    object Cafe {
      def buyCoffee(cc: CreditCard): Coffee = {
        val cup = Coffee()
        cc charge cup.price
        cup
      }
    }

    //how to do I f#$%& test this without charging the credit card?
    Cafe.buyCoffee(CreditCard("1234123", "11/20", 123)) should be(Coffee())
  }

  "Coffee shop version 1.1 with side effects in dependency injection" - {
    case class CreditCard(number: String, expDate: String, csv: Int)
    case class Coffee(price: Int = 2000)
    trait Payments {
      def charge(cc: CreditCard, amount: Int)
    }

    object Cafe {
      def buyCoffee(cc: CreditCard, p: Payments): Coffee = {
        val cup = Coffee()
        p charge(cc, cup.price)
        cup
      }
    }

    //Now we can test, but passing a mock
    val mockPayment: Payments = (_, _) => ()
    Cafe.buyCoffee(CreditCard("1234123", "11/20", 123), mockPayment) should be(Coffee())
  }

  "Coffee shop version 1.2 with curried injection" - {
    case class CreditCard(number: String, expDate: String, csv: Int)
    case class Coffee(price: Int = 2000)
    trait Payments {
      def charge(cc: CreditCard, amount: Int)
    }

    object Cafe {

      val buyCoffee2: CreditCard => Payments => Coffee = cc => p => {
        val cup = Coffee()
        p charge(cc, cup.price)
        cup
      }

      def buyCoffee(cc: CreditCard)(p: Payments): Coffee = {
        val cup = Coffee()
        p charge(cc, cup.price)
        cup
      }
    }

    //Now we can test generating a new function with just the credit card and the payment can be selected latter
    val buyCoffeeWithMyCC = Cafe.buyCoffee(CreditCard("1234123", "11/20", 123)) _
    val mockPayment: Payments = (_, _) => ()
    buyCoffeeWithMyCC(mockPayment) should be(Coffee())
  }

  "Coffee show version 2.0 without side effects" - {
    case class CreditCard(number: String, expDate: String, csv: Int)
    case class Coffee(price: Int = 2000)
    case class Charge(cc: CreditCard, amount: Int)

    object Cafe {
      def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
        val cup = Coffee()
        (cup, Charge(cc, cup.price))
      }
    }

    //Now we don't even need a mock, we just return a description for what we are doing and somewhere else we charge the credit card
    val cc = CreditCard("1234123", "11/20", 123)
    Cafe.buyCoffee(cc) should be((Coffee(), Charge(cc, Coffee().price)))

    //And now some functional magic
    def combine(c1: Charge, c2: Charge) = {
      if (c1.cc == c2.cc)
        Charge(c1.cc, c1.amount + c2.amount)
      else
      //SIDE EFFECT ALERT -> please omit this until we see how to deal with errors
        throw new Exception("Cant charge two different cards")
    }

    def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
      val purchases: List[(Coffee, Charge)] = List.fill(n)(Cafe.buyCoffee(cc))
      val (coffees, charges) = purchases.unzip
      (coffees, charges.reduce(combine))
    }

    //Now we can buy as many coffees as we want and charge the credit card just once with the total amount
    buyCoffees(cc, 10) should be((List.fill(10)(Coffee()), Charge(cc, 10 * Coffee().price)))

    def merge(charges: List[Charge]): List[Charge] =
      charges.groupBy(_.cc).values.map(_.reduce(combine)).toList

    val cc2 = CreditCard("34566", "12/22", 143)
    //We can even join many charges in a single one
    merge(List(Charge(cc, 1000), Charge(cc, 2000), Charge(cc2, 3000))) should be(List(Charge(cc2, 3000), Charge(cc, 3000)))
  }
}
