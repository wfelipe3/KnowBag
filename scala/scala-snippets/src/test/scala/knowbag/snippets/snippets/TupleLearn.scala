package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/04/15.
 */
class TupleLearn extends FlatSpec with Matchers {

  "Tuple allow to have more than one grouped value and" should "be referenced by ordinal number" in {
    val tuple: Tuple2[String, Int] = ("felipe", 27)
    tuple._1 should be("felipe")
    tuple._2 should be(27)
  }

  it should "be referenced by name too" in {
    val (name, age) = ("felipe", 27)
    name should be("felipe")
    age should be(27)
  }

}
