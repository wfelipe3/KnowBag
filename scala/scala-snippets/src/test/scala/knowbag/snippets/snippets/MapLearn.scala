package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/29/15.
 */
class MapLearn extends FlatSpec with Matchers {

  behavior of "maps"

  it should "create maps with apply method with the syntax Map(key -> value)" in {
    val map = Map("felipe" -> 27, "juan" -> 25)
    val map2 = map + ("jose" -> 23)
    map2 should be(Map("felipe" -> 27, "juan" -> 25, "jose" -> 23))
  }

  it should "be possible to map capitals with pattern matching" in {
    val ages: Map[String, Int] = Map("felipe" -> 27, "juan" -> 25, "jose" -> 23, "maria" -> 26)
    val upperAges = ages.map {
      kv => (kv._1.toUpperCase, kv._2)
    }

    val lowerAges = upperAges.map {
      case (k, v) => (k.toLowerCase(), v)
    }

    upperAges should be(Map("FELIPE" -> 27, "JUAN" -> 25, "JOSE" -> 23, "MARIA" -> 26))
    lowerAges should be(Map("felipe" -> 27, "juan" -> 25, "jose" -> 23, "maria" -> 26))
  }

  "sets" should "be created using apply method" in {
    val set = Set("felipe", "juan", "jose", "maria", "felipe")
    val upperSet = set map (_.toUpperCase)
    upperSet should be(Set("FELIPE", "JUAN", "JOSE", "MARIA"))

    val adder = add _
    adder(4,5) should be(9)
  }

  def add(x: Int, y: Int) = x + y
}
