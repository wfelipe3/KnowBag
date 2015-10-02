package knowbag.fpic.c7

import knowbag.fpic.c7.Par._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 8/3/15.
 */
class ParApiTest extends FlatSpec with Matchers {

  "Parallelism api" should "compute elements in parallel" in {
    def sum(ints: Seq[Int]): Par[Int] =
      if (ints.size == 1)
        Par.unit(ints.head)
      else {
        val (left, right) = ints.splitAt(ints.size / 2)
        Par.map2(Par.fork(sum(left)), Par.fork(sum(right)))(_ + _)
      }
  }
}
