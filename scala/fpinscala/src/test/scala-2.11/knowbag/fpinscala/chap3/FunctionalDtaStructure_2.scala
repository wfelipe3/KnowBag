package knowbag.fpinscala.chap3

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by WilliamE on 08/04/2016.
  */
class FunctionalDtaStructure_2 extends FlatSpec with Matchers {

  behavior of "List"

  it should "create a list of elements and the sum should be the sum of all the elements" in {
    val list: List[Int] = List(1, 2, 3, 4, 5)
    List.sum(list) should be(1 + 2 + 3 + 4 + 5)
  }

  it should "multiply all elements in the list" in {
    val list = List(1, 2, 3, 4, 5)
    List.product(list) should be(1 * 2 * 3 * 4 * 5)
    List.product(List(0, 1, 2, 3, 4, 5)) should be(0)
  }

  "Exercise 3.1" should "return the result of a given expression" in {
    val x = List(1, 2, 3, 4, 5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + List.sum(t)
      case _ => 101
    }

    x should be(3)
  }

  "Exercise 3.2" should "implement tail in list" in {
    a[NilListException] shouldBe thrownBy(List.tail(Nil))
    List.tail(List(1)) should be(Nil)
    List.tail(List(1, 2)) should be(List(2))
    List.tail(List(1, 2, 3, 4, 5)) should be(List(2, 3, 4, 5))
  }

  "Exercise 3.3" should "implement setHead to replace the first element of a list" in {
    List.setHead(1, Nil) should be(List(1))
    List.setHead(2, List(1)) should be(List(2))
    List.setHead(2, List(1, 2, 3)) should be(List(2, 2, 3))
  }

  "Exercise 3.4" should "implement drop" in {
    a[NilListException] shouldBe thrownBy(List.drop(Nil, 10))
    a[NilListException] shouldBe thrownBy(List.drop(List(1), 2))
    List.drop(List(1, 2, 3, 4), 2) should be(List(3, 4))
    List.drop(List("test"), 0) should be(List("test"))
    List.drop(List(1, 2, 3, 4, 45, 5, 6, 7, 9), 5) should be(List(5, 6, 7, 9))
  }

  "Exercise 3.5" should "implement drop while" in {
    List.dropWhile(Nil, (a: Int) => true) should be(Nil)
    List.dropWhile(List(1), (a: Int) => a == 1) should be(Nil)
    List.dropWhile(List(34), (a: Int) => false) should be(List(34))
    List.dropWhile(List(10), (a: Int) => true) should be(Nil)
    List.dropWhile(List("pedro", "gomez", "lopez"), (n: String) => n.endsWith("o")) should be(List("gomez", "lopez"))
  }

  "Exercise 3.6" should "implement init without tail" in {
    List.init(Nil) should be(Nil)
    List.init(List(1)) should be(Nil)
    List.init(List(1, 2)) should be(List(1))
    List.init(List(1, 2, 3)) should be(List(1, 2))
  }

  "Exercise 3.7" should "implement fold right" in {
    List.foldRight[Int, Int](Nil, 0)(_ + _) should be(0)
    List.foldRight(List(1), 0)(_ + _) should be(1)
    List.foldRight(List(1, 2), 1)(_ + _) should be(4)
    List.foldRight(List("test", "message"), "")(_ + _) should be("testmessage")
  }

  "Exercise 3.8" should "test Nil and Cons" in {
    List.foldRight(List(1, 2, 3), Nil: List[Int])(Cons(_, _)) should be(List(1, 2, 3))
  }

  "Exercise 3.9" should "compute the length of a list with foldRight" in {
    List.length(Nil) should be(0)
    List.length(List("test")) should be(1)
    List.length(List(true, false, true)) should be(3)
  }

  "fold right with to many elements" should "throw Stack overflow error" in {
    a[StackOverflowError] shouldBe thrownBy(List.length(List.createList(1000000)))
  }

  "Exercise 3.10" should "implement fold left tail recursive" in {
    List.foldLeft(Nil: List[Int], 0)(_ + _) should be(0)
    List.foldLeft(List(1, 2, 4), 2)(_ + _) should be(2 + 1 + 2 + 4)
  }

  "Exercise 3.11" should "implement sum product and legth with fold left" in {
    List.sumFL(List(1, 2, 3, 4, 5)) should be(1 + 2 + 3 + 4 + 5)
    List.product(List(1, 2, 4, 4, 5)) should be(1 * 2 * 4 * 4 * 5)
    List.lengthFL(List("sdfl", "sdfsd", "sldfkj")) should be(3)
    List.lengthFL(List.createList(1000000)) should be(1000000)
  }

  "Exercise 3.12" should "implement reverse" in {
    List.reverse(Nil) should be(Nil)
    List.reverse(List(1, 2, 3)) should be(List(3, 2, 1))
  }

  "Exercise 3.13" should "implement foldRight in terms of foldLeft" in {
    List.foldRightLF(List(1, 2, 3), "")(_ + _) should be(List.foldRight(List(1, 2, 3), "")(_ + _))
    List.foldLeftLR(List(1, 2, 3), "")(_ + _) should be(List.foldLeft(List(1, 2, 3), "")(_ + _))
  }

  "Exercise 3.14" should "implement append in terms of fold left or fold right" in {
    List.appendFR(Nil, 1) should be(List(1))
    List.appendFR(List(1, 2, 3), 4) should be(List(1, 2, 3, 4))
  }

  "Exercise 3.15" should "implement concatenate" in {
    List.concat(List(List(1), List(2, 3), List(4))) should be(List(1, 2, 3, 4))
  }

  "Exercise 3.16" should "implement add1" in {
    List.add1(List(1, 2, 3, 4)) should be(List(2, 3, 4, 5))
  }

  "Exercise 3.17" should "implement convert double to String" in {
    List.convert(List(1, 2, 3.5)) should be(List("1.0", "2.0", "3.5"))
  }

  "Exercise 3.18" should "implement map" in {
    List.map(List(1, 2, 3))(_ + 1) should be(List(2, 3, 4))
    List.map(List(1, 2, 3.5))(_.toString) should be(List("1.0", "2.0", "3.5"))
  }

  "Exercise 3.19" should "implement filter" in {
    List.filter(List(1, 2, 3))(_ > 1) should be(List(2, 3))
  }

  "Exercise 3.20" should "implement flatmap" in {
    List.map(List(1, 2, 3))(i => List(i, i)) should be(List(List(1, 1), List(2, 2), List(3, 3)))
    List.flatMap(List(1, 2, 3))(i => List(i, i)) should be(List(1, 1, 2, 2, 3, 3))
  }

  "Exercise 3.21" should "implement flatmap with filter" in {
    List.filterWithFlatMap(List(1, 2, 3))(_ > 1) should be(List(2, 3))
  }

  "Exercise 3.22" should "implement add list function" in {
    List.addList(Nil, Nil) should be(Nil)
    List.addList(List(1), List(2)) should be(List(3))
    List.addList(List(1, 2), List(2, 5)) should be(List(3, 7))
  }

  "Exercise 3.23" should "implement zipWith" in {
    List.zipWith(List("a", "b"), List("c", "d"))((a, b) => a + b) should be(List("ac", "bd"))
  }

  "Exercise 3.24" should "implement hasSubsequence" in {
    List.hasSubSequence(List(1, 2, 3, 4, 5), List(1, 2)) should be(true)
    List.hasSubSequence(List(1, 2, 3, 4, 5), List(1, 3)) should be(false)
    List.hasSubSequence(List(1, 2, 3, 4, 5), List(3, 4, 5)) should be(true)
  }

  "Exercise 3.25" should "implement tree size" in {
    Tree.size(Leaf(3)) should be(1)
    Tree.size(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) should be(5)
  }

  "Exercise 3.26" should "implement max in tree" in {
    Tree.max(Leaf(1)) should be(1)
    Tree.max(Branch(Leaf(1), Leaf(2))) should be(2)
    Tree.max(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) should be(3)
  }

  "Exercise 3.27" should "implement depth in tree" in {
    Tree.depth(Leaf(1)) should be(1)
    Tree.depth(Branch(Leaf(1), Leaf(2))) should be(2)
    Tree.depth(Branch(Leaf(1), Branch(Leaf(2), Leaf(4)))) should be(3)
    Tree.depth(Branch(Leaf(1), Branch(Branch(Leaf(2), Leaf(5)), Leaf(4)))) should be(4)
  }

  "Exercise 3.28" should "implement map in tree" in {
    Tree.max(Tree.map(Leaf("1"))(_.toInt)) should be(1)
    Tree.max(Tree.map(Branch(Leaf("1"), Leaf("2")))(_.toInt)) should be(2)
  }

  behavior of "Exercise 3.29"

  it should "implement fold in tree" in {
    Tree.fold(Leaf(1), 0)(_ + _)(_ + _) should be(1)
  }

  it should "implement max via fold" in {
    Tree.maxViaFold(Leaf(1)) should be(1)
    Tree.maxViaFold(Branch(Leaf(1), Leaf(2))) should be(2)
    Tree.maxViaFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) should be(3)
  }

  it should "implement depth via fold" in {
    Tree.depth(Leaf(1)) should be(1)
    Tree.depth(Branch(Leaf(1), Leaf(2))) should be(2)
    Tree.depth(Branch(Leaf(1), Branch(Leaf(2), Leaf(4)))) should be(3)
    Tree.depth(Branch(Leaf(1), Branch(Branch(Leaf(2), Leaf(5)), Leaf(4)))) should be(4)
  }

  it should "implement size via fold" in {
    Tree.sizeViaFold(Leaf(3)) should be(1)
    Tree.sizeViaFold(Branch(Leaf(1), Branch(Leaf(2), Leaf(3)))) should be(5)
  }

  sealed trait List[+A]

  class NilListException extends Exception

  case object Nil extends List[Nothing]

  case class Cons[+A](head: A, tail: List[A]) extends List[A]

  object List {

    def sum(ints: List[Int]): Int =
      ints match {
        case Nil => 0
        case Cons(x, xs) => x + sum(xs)
      }

    def product(ints: List[Int]): Int =
      ints match {
        case Nil => 1
        case Cons(0, _) => 0
        case Cons(x, xs) => x * product(xs)
      }

    def tail[A](list: List[A]): List[A] =
      list match {
        case Nil => throw new NilListException
        case Cons(x, Nil) => Nil
        case Cons(x, xs) => xs
      }

    def setHead[A](a: A, list: List[A]): List[A] =
      list match {
        case Nil => List(a)
        case Cons(x, xs) => Cons(a, xs)
      }

    def drop[A](l: List[A], n: Int): List[A] = {
      if (n == 0) l
      else l match {
        case Nil => throw new NilListException
        case Cons(x, xs) => drop(xs, n - 1)
      }
    }

    def dropWhile[A](l: List[A], p: A => Boolean): List[A] =
      l match {
        case Cons(x, xs) if p(x) => dropWhile(xs, p)
        case _ => l
      }

    def init[A](l: List[A]): List[A] =
      l match {
        case Nil => Nil
        case Cons(x, Nil) => Nil
        case Cons(x, xs) => Cons(x, init(xs))
      }

    def foldRight[A, B](list: List[A], b: B)(f: (A, B) => B): B =
      list match {
        case Nil => b
        case Cons(x, xs) => f(x, foldRight(xs, b)(f))
      }

    def length[A](list: List[A]): Int =
      foldRight(list, 0)((_, n) => n + 1)

    def createList(n: Long): List[Long] = {
      def createList(n: Long, list: List[Long]): List[Long] = {
        if (n == 0) list
        else createList(n - 1, Cons(n, list))
      }
      createList(n, Nil)
    }

    def foldLeft[A, B](list: List[A], b: B)(f: (B, A) => B): B =
      list match {
        case Nil => b
        case Cons(x, xs) => foldLeft(xs, f(b, x))(f)
      }

    def sumFL(list: List[Int]): Int =
      foldLeft(list, 0)(_ + _)

    def productFL(list: List[Int]): Int =
      foldLeft(list, 1)(_ * _)

    def lengthFL[A](list: List[A]): Long =
      foldLeft(list, 0)((n, _) => n + 1)

    def reverse[A](list: List[A]): List[A] =
      foldLeft(list, Nil: List[A])((subList, a) => Cons(a, subList))

    def foldRightLF[A, B](l: List[A], z: B)(f: (A, B) => B): B =
      foldLeft(l, (b: B) => b)((g, a) => b => g(f(a, b)))(z)

    def foldLeftLR[A, B](l: List[A], z: B)(f: (B, A) => B): B =
      foldRight(l, (b: B) => b)((a, g) => b => g(f(b, a)))(z)

    def appendFR[A](l: List[A], a: A): List[A] =
      foldRight(l, List(a))(Cons(_, _))

    def append[A](a1: List[A], a2: List[A]): List[A] =
      a1 match {
        case Nil => a2
        case Cons(x, xs) => Cons(x, append(xs, a2))
      }

    def concat[A](l: List[List[A]]): List[A] =
      foldRight(l, Nil: List[A])(append)

    def add1(l: List[Int]): List[Int] =
      foldRight(l, Nil: List[Int])((a, b) => Cons(a + 1, b))

    def convert(l: List[Double]): List[String] =
      foldRight(l, Nil: List[String])((a, b) => Cons(a.toString, b))

    def map[A, B](as: List[A])(f: A => B): List[B] =
      foldRight(as, Nil: List[B])((a, b) => Cons(f(a), b))

    def filter[A](l: List[A])(p: A => Boolean): List[A] =
      foldRight(l, Nil: List[A])((a, b) => {
        if (p(a)) Cons(a, b)
        else b
      })

    def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] =
      concat(map(l)(f))

    def filterWithFlatMap[A](l: List[A])(p: A => Boolean): List[A] =
      flatMap(l)(a => if (p(a)) List(a) else Nil)

    def addList(l1: List[Int], l2: List[Int]): List[Int] =
      (l1, l2) match {
        case (Nil, _) => Nil
        case (_, Nil) => Nil
        case (Cons(x, xs), Cons(y, ys)) => Cons(x + y, addList(xs, ys))
      }

    def zipWith[A](l1: List[A], l2: List[A])(f: (A, A) => A): List[A] =
      (l1, l2) match {
        case (Nil, _) => Nil
        case (_, Nil) => Nil
        case (Cons(x, xs), Cons(y, ys)) => Cons(f(x, y), zipWith(xs, ys)(f))
      }

    def hasSubSequence[A](sup: List[A], sub: List[A]): Boolean =
      sup match {
        case Nil => if (sub == Nil) true else false
        case _ if startsWith(sup, sub) => true
        case Cons(x, xs) => hasSubSequence(xs, sub)
      }

    def startsWith[A](sup: List[A], sub: List[A]): Boolean =
      (sup, sub) match {
        case (_, Nil) => true
        case (Nil, _) => false
        case (Cons(x, xs), Cons(y, ys)) =>
          if (x == y) startsWith(xs, ys)
          else false
      }

    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))
  }

  sealed trait Tree[+A]

  case class Leaf[A](value: A) extends Tree[A]

  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  object Tree {
    def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
      tree match {
        case Leaf(a) => Leaf(f(a))
        case Branch(l, r) => Branch(map(l)(f), map(r)(f))
      }

    def max(tree: Tree[Int]): Int =
      tree match {
        case Leaf(a) => a
        case Branch(l, r) => max(l).max(max(r))
      }

    def maxViaFold(t: Tree[Int]): Int =
      fold(t, 0)(_.max(_))(_.max(_))

    def size[A](t: Tree[A]): Int =
      t match {
        case Leaf(_) => 1
        case Branch(l, r) => 1 + size(l) + size(r)
      }

    def sizeViaFold[A](t: Tree[A]): Int =
      fold(t, 0)((_, b) => 1 + b)(1 + _ + _)

    def depth[A](t: Tree[A]): Int =
      t match {
        case Leaf(_) => 1
        case Branch(l, r) => 1 + depth(l).max(depth(r))
      }

    def depthViaFold[A](t: Tree[A]): Int =
      fold(t, 0)((_, b) => b + 1)((tl, tr) => 1 + tl.max(tr))

    def fold[A, B](t: Tree[A], b: B)(f: (A, B) => B)(g: (B, B) => B): B =
      t match {
        case Leaf(a) => f(a, b)
        case Branch(l, r) => g(fold(l, b)(f)(g), fold(r, b)(f)(g))
      }
  }

}
