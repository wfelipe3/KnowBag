package knowbag.functional.state

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/19/15.
 */
class StateTest extends FlatSpec with Matchers {

  "a state" should "have the value for the current state ant the transition to the next one" in {
    val state = State[String, Integer](state => (10, s"$state value"))
    val finalState = state.map(_ * 2).run("nice")
    println(finalState)
  }

}
