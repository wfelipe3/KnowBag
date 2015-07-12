package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/29/15.
 */
class CollectionOperationLearn extends FlatSpec with Matchers {

  "filter" should "match for elements with the given function and return a new list with the elements that pass the function" in {
    def filter[T](list: List[T])(p: T => Boolean): List[T] = {
      list match {
        case Nil => Nil
        case x :: xs => if (p(x)) x :: filter(xs)(p) else filter(xs)(p)
      }
    }

    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    filter(list)(_ % 2 == 0) should be(List(2, 4, 6, 8))
    list.filter(_ % 2 == 0) should be(List(2, 4, 6, 8))
  }

  "map" should "transform all elements in the list with the given function" in {
    def map[T, U](list: List[T], p: T => U): List[U] = list match {
      case Nil => Nil
      case x :: xs => p(x) :: map(xs, p)
    }

    val list = List("1", "2", "3", "4", "5")
    list.map(_.toInt) should be(List(1, 2, 3, 4, 5))
    map(list, (x: String) => x.toInt) should be(List(1, 2, 3, 4, 5))
  }

  "list of 'a''a''a''b''c''c''a'" should "be List(aaa) list(b) List(cc) List(a)" in {
    def pack[T](list: List[T]): List[List[T]] = list match {
      case Nil => Nil
      case x :: xs =>
        val (fist, rest) = list.span(_ == x)
        fist :: pack(rest)
    }

    def encode[T](list: List[T]): List[(T, Int)] = {
      pack(list).map(x => (x.head, x.length))
    }

    val list: List[String] = List("a", "a", "a", "a", "b", "c", "c", "a")
    pack(list) should be(List(List("a", "a", "a", "a"), List("b"), List("c", "c"), List("a")))
    encode(list) should be(List(("a", 4), ("b", 1), ("c", 2), ("a", 1)))
  }

  "reduce" should "convert elements in a list to one combined element with a function" in {
    val ints: List[Int] = List(1, 3, 4, 5, 6, 7, 8)
    val sum = ints.reduceLeft(_ + _)
    val mult = ints.reduceLeft(_ * _)
    sum should be(1 + 3 + 4 + 5 + 6 + 7 + 8)
    mult should be(1 * 3 * 4 * 5 * 6 * 7 * 8)
  }

  "fold" should "convert elements in a list to one combined element with a given function and an initial parameter" in {
    val ints: List[Int] = List(1, 3, 4, 5, 6, 7, 8)
    ints.foldLeft(2)(_ + _) should be(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8)
    (2 :: 3 :: 4 :: 5 :: Nil).foldRight("10")(_ + _) should be("234510")
    (2 :: 3 :: 4 :: 5 :: Nil).foldLeft("10")(_ + _) should be("102345")

    ((2 :: 3 :: 4 :: 5 :: Nil) foldRight (1:: 2 :: 3 :: 4 :: Nil)) (_ :: _)
  }
}
