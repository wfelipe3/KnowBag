package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

import scala.{Some, None}

/**
 * Created by feliperojas on 6/11/15.
 */
class FunctionalLearning extends FlatSpec with Matchers {

  behavior of "Curring, this is when a function that takes many parameters is transformed in multiple functions that take single params"

  it should "create new functions from existing ones when they have multiple params" in {
    def cat1(s1: String)(s2: String) = s1 + s2
    def cat2(s1: String) = (s2: String) => s1 + s2

    // cat = hello + s1 with the replaced curried version
    val cat: (String) => String = cat2("hello ")
    cat("world") should be("hello world")

    // subCat = hello + s1 with the replaced curried version
    def subCat = cat1("hello ") _
    subCat("world") should be("hello world")
  }

  it should "create curried functions from regular single argument params functions with .curried" in {
    def cat(s1: String, s2: String) = s1 + s2
    // curriedCat = (String) => (String => String)
    def curriedCat = (cat _).curried

    def otherCat(s: String, i: Int) = s"$s $i"
    def curriedOtherCat: String => Int => String = (otherCat _).curried

    curriedCat("hello ")("world") should be("hello world")
    curriedOtherCat("hello")(5) should be("hello 5")
  }

  it should "create curried values with val too" in {
    val f1: String => String => String = (s1: String) => (s2: String) => s1 + s2
    val f2: String => (String => String) = (s1: String) => (s2: String) => s1 + s2

    //f1("hello")("world")
    //((s2: String) => "hello" + s2)("world")
    //"hello" + "world"
    //"helloworld"
    f1("hello")("world") should be("helloworld")
    f2("hello")("world") should be("helloworld")
  }

  it should "uncurried functions with .uncurried method" in {
    def curried(s1: String)(s2: String) = s1 + s2
    def uncurried = Function.uncurried(curried _)
    uncurried("hello", "world") should be("helloworld")

    val helloWorldCurried: (String) => (String) => (String) = (s1: String) => (s2: String) => s"$s1 $s2"
    val helloWorldUnCurried = Function.uncurried(helloWorldCurried)

    helloWorldUnCurried("hello", "world") should be("hello world")
  }

  it should "use function to pass a tuple as a function argument" in {
    def mult(v1: Int, v2: Int, v3: Int) = v1 * v2 * v3
    def tupled = Function.tupled(mult _)

    val tuple = (1,2,4)

    tupled(tuple) should be (1 * 2 * 4)
  }

  behavior of "partial functions throw match error when the case does not match, but with lift method, patial functions return option"

  it should "transform partial function of value to partial function of some value and return None instead match error" in {
    val partialFunction: PartialFunction[String, Int] = {
      case "5" => 5
    }

    partialFunction("5") should be (5)
    a[MatchError] shouldBe thrownBy(partialFunction("1"))

    val liftPartialFunction = partialFunction.lift
    liftPartialFunction("6") should be(None)
    liftPartialFunction("5") should be(Some(5))

    val x: (Int) => String = (v: Int) => v.toString
    val len = x andThen(_.length)
    len(10) should be(2)
  }


}
