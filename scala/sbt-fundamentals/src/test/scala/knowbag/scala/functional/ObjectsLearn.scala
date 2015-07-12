package knowbag.scala.functional

import org.scalatest.FunSuite

/**
 * Created by feliperojas on 17/02/15.
 */

class ObjectsLearn extends FunSuite {

  test("Objects as singletons NO CONSTRUCTOR PARAMETERS ALLOWED") {
    HitCounter.add()
    HitCounter.add()
    assert(HitCounter.getCount === 2)
  }
}
