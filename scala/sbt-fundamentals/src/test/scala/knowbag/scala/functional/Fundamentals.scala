package knowbag.scala.functional

import org.scalatest.FunSuite

import scala.annotation.tailrec
import scala.collection.immutable.SortedMap
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Sorting

/**
 * Created by feliperojas on 23/01/15.
 */
class Fundamentals extends FunSuite {

  test("Get the 5th char in a String") {
    assert("this is a test"(5) === 'i')
    assert("this is a test".apply(5) === 'i')
  }

  test("create Array with apply") {
    assert(Array(1, 2, 3, 4).mkString(",") === "1,2,3,4")
    assert(Array.apply(1, 2, 3, 4).mkString(",") === "1,2,3,4")
  }

  test("count characters in an array") {
    assert("this is a test".count(value => {
      if (value == 'i')
        true
      else
        false
    }) === 2)
    assert("count OF Upper Case".count(_.isUpper) === 4)
    assert("this is a test".count(_ == 'i') === 2)
  }

  test("if as a value") {
    def ifFunction(x: Int): Any = {
      if (x == 5) "yes" else ()
    }
    val valIfFunction: Int => Any = (x: Int) => {
      if (x == 5) "yes" else ()
    }
    assert(ifFunction(5) === "yes")
    assert(ifFunction(6) ===())
    assert(valIfFunction(5) === "yes")
    assert(valIfFunction(6) ===())
  }

  test("for") {
    val values = Array(0, 1, 2, 3, 4)
    for (i <- 0 until values.length) {
      assert(values(i) === i)
    }
    val otherValues = for (x <- values; from = 2; i <- from to 2; if x % 2 == 0) yield x * i
    assert(otherValues === Array(0, 4, 8))
  }

  test("factorial") {
    def forFactorial(x: Int): Int = {
      var r = 1
      for (i <- 1 to x) r = r * i
      r
    }

    def factorial(x: Int): Int = {
      @tailrec
      def loop(acum: Int, y: Int): Int = {
        if (y == 1)
          acum
        else
          loop(y * acum, y - 1)
      }
      loop(1, x)
    }

    assert(forFactorial(5) === factorial(5))
    assert(factorial(5) === 120)
  }

  test("default values") {
    def decorateString(left: String = "[", value: String, right: String = "]"): String = {
      s"$left$value$right"
    }

    assert(decorateString(value = "test") === "[test]")
    assert(decorateString("<<", "test", ">>") === "<<test>>")
  }

  test("variable arguments") {
    def imperativeSum(value: Int*): Int = {
      var sum = 0
      for (x <- value) sum = sum + x
      sum
    }
    def recursiveSum(value: Int*): Int = {
      def tailSum(sum: Int, value: Int*): Int = {
        if (value.isEmpty)
          sum
        else
          tailSum(sum + value.head, value.tail: _*)
      }
      tailSum(0, value: _*)
    }
    assert(imperativeSum(1, 2, 3, 4, 5) === 1 + 2 + 3 + 4 + 5)
    assert(imperativeSum(1 to 5: _*) == 1 + 2 + 3 + 4 + 5)
    assert(recursiveSum(1 to 5: _*) == 1 + 2 + 3 + 4 + 5)
  }

  test("procedure") {
    def printMessage(mkMessage: () => String) {
      println(mkMessage())
    }
    assert(printMessage(() => {
      "test"
    }) ===())
    //    assert(printMessage(mkMessage("test")) === ())
  }

  test("lazy eval") {
    lazy val word = Source.fromFile("/usr/test/file.txt").mkString
    assert(true === true)
  }

  test("throw exception") {
    intercept[IllegalArgumentException] {
      throw new IllegalArgumentException
    }
  }

  test("exception as nothing") {
    def exceptionFun(x: Int) = {
      if (x != 0) x + x
      else throw new IllegalArgumentException
    }
    assert(exceptionFun(5) === 10)
  }

  test("try catch") {
    def exceptionFun(x: Int) = {
      if (x != 0) x + x
      else throw new IllegalArgumentException
    }

    try {
      val x = exceptionFun(0)
      fail()
    } catch {
      case _: IllegalArgumentException => "nice"
      case ex: Exception => s"bad error $ex"
    }

  }

  test("array ArrayOps") {
    val nums = ArrayBuffer[Int]()
    nums += 5
    val array2 = nums ++ Array(1, 3, 4)
    assert(nums(0) === 5)
    assert(array2(0) === 5)
    assert(array2(1) === 1)
  }

  test("filter map") {
    val nums = Array(1, 4, 3, 5, 2)
    val decsNums = nums.filter(_ % 2 == 0)
      .map(2 * _)
      .sortWith(_ > _)
    val ascNums = new Array[Int](decsNums.length)
    decsNums.copyToArray(ascNums)
    Sorting.quickSort(ascNums)
    assert(decsNums === Array(8, 4))
    assert(ascNums === Array(4, 8))
    assert(ascNums.mkString(" ") === "4 8")
  }

  test("map") {
    val map = Map("felipe" -> 20, "juan" -> 18, "jose" -> 16, ("william", 18))
    val mutableMap = scala.collection.mutable.Map("felipe" -> 20, "juan" -> 18, "jose" -> 16, ("william", 18))
    val sortedMap = SortedMap("william" -> 18, "felipe" -> 20, "juan" -> 18, "jose" -> 16)

    val otherMap = map + ("pepe" -> 70)

    assert(map.getOrElse("pepe", -1) === -1)
    assert(otherMap.getOrElse("pepe", -1) === 70)
    assert(map("felipe") === 20)
  }

  test("discount") {
    def discountValues(prices: Map[String, Int], discount: Int): Map[String, Int] = {
      def innerDiscount(discountedPrices: Map[String, Int], prices: Map[String, Int]): Map[String, Int] = {
        if (prices.isEmpty)
          discountedPrices
        else {
          val (name, value): (String, Int) = prices.head
          val valueWithDiscount: Int = value - discount
          innerDiscount(discountedPrices + (name -> valueWithDiscount), prices.tail)
        }
      }
      innerDiscount(Map(), prices)
    }

    assert(discountValues(Map("1" -> 100, "2" -> 200), 10) === Map("1" -> 90, "2" -> 190))
  }

  test("values lenghts") {
    def lenghtValues(values: Array[Int], v: Int): (Int, Int, Int) = {
      def innerLenghtValues(values: Array[Int], tuple: (Int, Int, Int)): (Int, Int, Int) = {
        if (values.isEmpty)
          tuple
        else {
          val head = values.head
          if (head < v) innerLenghtValues(values.tail, (tuple._1 + 1, tuple._2, tuple._3))
          else if (head == v) innerLenghtValues(values.tail, (tuple._1, tuple._2 + 1, tuple._3))
          else innerLenghtValues(values.tail, (tuple._1, tuple._2, tuple._3 + 1))
        }
      }
      innerLenghtValues(values, (0, 0, 0))
    }

    assert(lenghtValues(Array(1, 2, 3, 4, 5, 6, 7, 8), 5) ===(4, 1, 3))
  }

  test("traverse map") {
    val map = Map("felipe" -> 20, "juan" -> 18, "jose" -> 16, ("william", 18))
    val mapValues = for ((key, value) <- map) yield (value -> key)
    assert(mapValues === Map(20 -> "felipe", 18 -> "juan", 16 -> "jose", 18 -> "william"))
  }

  test("tuple") {
    val tuple = ("felipe", "rojas")
    val (name, lastName) = ("felipe", "rojas")
    assert(tuple._1 === "felipe")
    assert(tuple._2 === "rojas")
    assert(name === "felipe")
    assert(lastName === "rojas")
  }

  test("zip") {
    val namesAges = List("felipe", "juan", "jose", "william").zip(List(26, 24, 22, 24)).toMap
    assert(namesAges === Map("felipe" -> 26, "juan" -> 24, "jose" -> 22, "william" -> 24))
  }

  test("create class object") {
    val person = new Person(lastName = "rojas")
    val person2 = new Person("rojas", "felipe")
    person.name = "felipe"
    person.name_=("felipe")
    person.age = 10
    person.incrementAge()
    assert(person.name === "felipe")
    assert(person.age === 11)
    assert(person.lastName === "rojas")
    assert(person.getFullName === "felipe rojas")
  }

  test("count change") {
    def countChange(value: Int, coins: List[Int]): Int = {
      if (coins.isEmpty)
        0
      else if (value < 0)
        0
      else if (value == 0)
        1
      else
        countChange(value - coins.head, coins) + countChange(value, coins.tail)
    }

    assert(countChange(5, List[Int]()) === 0)
    assert(countChange(2, List[Int](5)) === 0)
    assert(countChange(1, List[Int](1)) === 1)
  }
}
