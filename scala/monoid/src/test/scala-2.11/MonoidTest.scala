import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 8/31/15.
 */
class MonoidTest extends FlatSpec with Matchers {

  behavior of "Monoid"

  it should "be associative and have an identity value" in {
    stringMonoid.op(stringMonoid.op("felipe", " "), "rojas") should be(stringMonoid.op("felipe", stringMonoid.op(" ", "rojas")))
    stringMonoid.op(stringMonoid.zero, "felipe") should be("felipe")
  }

  it should "handle lists too" in {
    def listMonoid[A] = new Monoid[List[A]] {
      def op(a1: List[A], a2: List[A]): List[A] = a1 ++ a2

      def zero: List[A] = List.empty[A]
    }

    listMonoid[Int].op(List(1, 2, 3), listMonoid[Int].op(List(4, 5, 6), List(7, 8, 9))) should be(listMonoid[Int].op(listMonoid[Int].op(List(1, 2, 3), List(4, 5, 6)), List(7, 8, 9)))
    listMonoid[Int].op(List(1, 2, 3), listMonoid.zero) should be(List(1, 2, 3))
  }

  "int addition" should "add to integers" in {
    val intMonoid = new Monoid[Int] {
      def op(a1: Int, a2: Int): Int = a1 + a2

      def zero: Int = 0
    }

    intMonoid.op(1, 2) should be(3)
    intMonoid.op(intMonoid.zero, 32) should be(32)
  }

  "option monoid" should "return second option if first is not present" in {
    def optionMonoid[A] = new Monoid[Option[A]] {
      def op(a1: Option[A], a2: Option[A]): Option[A] = a1.orElse(a2)

      def zero: Option[A] = None
    }

    optionMonoid.op(Some(3), Some(4)) should be(Some(3))
    optionMonoid.op(optionMonoid.zero, Some(4)) should be(Some(4))
  }

  "endofunction monoid" should "return function composition" in {
    endoMonoid.op((a: Int) => {
      a * 2
    }, (a: Int) => {
      a / 2
    }).apply(10) should be(10)
  }

  "fold right with monoid" should "be easy :P" in {
    val stringConcat = stringMonoid
    val words = List("hic", "est", "index")
    words.foldRight(stringMonoid.zero)(stringConcat.op) should be ("hicestindex")
    words.foldLeft(stringMonoid.zero)(stringMonoid.op) should be ("hicestindex")
  }

  "foldMap" should "return a instance of map type with a monoid" in {
    def foldMap[A, B](list: List[A], monoid: Monoid[B])(f: A => B): B = {
      list.foldLeft(monoid.zero)((b, a) => monoid.op(b, f(a)))
    }

    def foldRight[A, B](list: List[A])(b: B)(f: (A, B) => B): B = {
      val curried: A => B => B = f.curried
      foldMap(list, endoMonoid[B])(f.curried)(b)
    }

    foldMap(List(1,2,3,4), stringMonoid)(a => a.toString) should be ("1234")
  }

  def stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String): String = a1 + a2

    def zero: String = ""
  }

  def endoMonoid[A] = new Monoid[A => A] {
    def op(a1: (A) => A, a2: (A) => A): (A) => A = a1.compose(a2)
    def zero: (A) => A = (a: A) => a
  }
}
