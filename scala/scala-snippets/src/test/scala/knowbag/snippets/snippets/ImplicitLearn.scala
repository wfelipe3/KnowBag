package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

class ImplicitLearn extends FlatSpec with Matchers {

  behavior of "implicit"

  it should "pass implicit values without explicit calling method with implicit val" in {
    def impilictTest(implicit x: Int): Int = {
      x + x
    }

    implicit val x = 10

    impilictTest should be(20)
  }

  it should "pass implicit functions when they dont have parameters or those parameters are implicit too" in {
    def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate

    object SimpleStateSalesTax {
      implicit val rate = 0.5f
    }

    case class ComplexStateTaxData(baseRate: Float, isTaxHoliday: Boolean, storeId: Int)

    object ComplexStateSalesTax {
      implicit def rate(implicit data: ComplexStateTaxData): Float = {
        if (data.isTaxHoliday)
          0.0f
        else
          data.baseRate + data.storeId * 0.2f
      }
    }

    {
      import SimpleStateSalesTax.rate

      calcTax(10) should be(5)
    }

    {
      import ComplexStateSalesTax.rate

      implicit val complexData = ComplexStateTaxData(0.5f, true, 2)
      calcTax(10) should be(0f)
    }
  }

  it should "be possible to use implicitly notation" in {
    case class MyList[A](list: List[A]) {
      def sortByOne[B](f: A => B)(implicit ord: Ordering[B]): List[A] =
        list.sortBy(f)(ord)

      def sortByTwo[B: Ordering](f: A => B): List[A] =
        list.sortBy(f)(implicitly[Ordering[B]])
    }

    val list = MyList(List(1, 2, 3, 4, 5, 6, 6, 7, 8, 9))

    println(list.sortByOne(i => -i))
    println(list.sortByTwo(i => -i))
  }

}
