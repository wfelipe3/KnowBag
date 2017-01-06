package com.knowbag.freemonad

import com.knowbag.freemonad.program.FreeMonadProgram
import org.scalatest.FunSuite

/**
  * Created by dev-williame on 1/4/17.
  */
class FreeMonadTest extends FunSuite {

  test("Free monad with OptionInterpreter") {
    val oresult = FreeMonadProgram.runWithOption
    assert(oresult.isEmpty)
    assert(oresult.getOrElse(List()) == List())
  }
}
