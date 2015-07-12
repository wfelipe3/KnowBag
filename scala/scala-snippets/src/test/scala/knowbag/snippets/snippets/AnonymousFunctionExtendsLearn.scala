package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 15/04/15.
 */
class AnonymousFunctionExtendsLearn extends FlatSpec with Matchers {
  
  behavior of "Function extends"
  
  it should "be possible to extend an anonymous function in order to pass a class as a function" in {
    class Transformer extends (String => String) {
      override def apply(v1: String): String = s"$v1 transformed"
    }

    def useTransformer(value: String, f: String => String) = {
      f(value)
    }

    useTransformer("test", new Transformer) should be("test transformed")
  }

}
