package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 14/04/15.
 */
class EnumerationLearning extends FlatSpec with Matchers {

  behavior of "enumerations"

  it should "be defined extending Enumeration, so it is not related to java enums" in {
    Breed.values.foreach(println)
  }

  it should "be possible to reference enums by their type" in {
    import Month._
    def isChristmas(month: Month) = month == Month.dec
    isChristmas(Month.apr) should not be(true)
    isChristmas(Month.dec) should be(true)
  }

  object Breed extends Enumeration {
    type Breed = Value
    val doberman = Value("Doberman dog")
    val yorkie = Value("romina")
    val scottie = Value("Scottish")
  }

  object Month extends Enumeration {
    type  Month = Value
    val jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec = Value
  }

}
