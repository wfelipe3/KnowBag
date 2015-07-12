package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 6/16/15.
 */
class CollectionsLearn extends FlatSpec with Matchers {

  behavior of "Sequence is the trait that all sequence collections like mutable list and immutable list extend"

  it should "create new list with the apply method and with the cons method" in {
    val list1 = List(1, 2, 3, 4, 5)
    val list2 = "felipe" :: "rojas" :: list1

    list2 should be("felipe" :: "rojas" :: 1 :: 2 :: 3 :: 4 :: 5 :: Nil)
    list2 should be(List("felipe", "rojas", 1, 2, 3, 4, 5))
  }

  it should "concatenate lists with ++ operator" in {
    val list1 = List(1, 2, 3, 4, 5)
    val list2 = List(6, 7, 8, 9)

    list1 ++ list2 should be(List(1, 2, 3, 4, 5, 6, 7, 8, 9))
  }

  it should "usual to return and use Seq in functions instead list so it can return and receive any implementation" in {
    val seq = Seq(1, 2, 3, 4)
    val seq2 = Set(5, 6, 7, 8)

    "felipe" +: "rojas" +: seq should be(Seq("felipe", "rojas", 1, 2, 3, 4))
    seq ++ seq2 should be(Seq(1, 2, 3, 4, 5, 6, 7, 8))
    1 +: 2 +: 3 +: Seq.empty should be(Seq(1, 2, 3))

    "william" +: Seq("felipe", "rojas") :+ "echeverri" should be(Seq("william", "felipe", "rojas", "echeverri"))
    Seq("william") :+ "felipe" :+ "rojas" should be(Seq("william", "felipe", "rojas"))
  }
}
