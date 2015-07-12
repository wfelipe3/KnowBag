package knowbag.snippets.snippets

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/11/15.
 */
class StreamLearn extends FlatSpec with Matchers {

  "given Stream range from 1 to 10 take 3" should "process only 3 elements instead of 10" in {

    def streamRange(from: Int, to: Int): Stream[Int] = {
      print(s"$from ")
      if (from >= to) Stream.empty
      else Stream.cons(from, streamRange(from + 1, to))
    }

    streamRange(1, 10).take(3)
  }

  "a stream can be created from a list" should
    "the stream method should return the stream representation for the list" in {
    val stream = List(1, 2, 4, 45, 7, 8, 8).toStream
    val even = stream.filter(_ % 2 == 0)

    even should be(List(2, 4, 8, 8))
  }

  "a stream can represent an infinite collection where values" should "be evaluated within the asked range" in {
    def from(n: Int): Stream[Int] = Stream.cons(n, from(n + 1))
    val natural = from(0)
    val m4 = natural.map(_ * 4)
    println(from(0).take(100).map(_ * 2).toList)
    println(m4.take(10).toList)
  }
}
