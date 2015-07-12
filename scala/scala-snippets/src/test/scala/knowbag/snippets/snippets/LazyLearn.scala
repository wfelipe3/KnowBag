package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 15/04/15.
 */
class LazyLearn extends FlatSpec with Matchers {

  behavior of "lazy val"

  it should "be initialized only the first time is used" in {
    var temp = "Test"
    def init = temp = temp + "init"
    lazy val initLazy = init

    temp should be("Test")
    initLazy
    temp should be("Testinit")
  }
}
