package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/04/15.
 */
class StringLiteralLearn extends FlatSpec with Matchers {

  "Strings can have interpolation with $ character and" should "replace the character and the token with the value" in {
    val message = "hello world"
    s"this is a test of values $message" should be("this is a test of values hello world")
    s"1 plus 2 is equal to ${plus(1,2)}" should be("1 plus 2 is equal to 3")
  }

  "String multi line is made using three double quotes and" should " allow multiple lines" in {
      """
        |this is a test
        |for a multiline String
      """.stripMargin should be("""
this is a test
for a multiline String
      """)
  }

  "Multi string can have string interpolation to and" should "return multiple lines string with interpolated values" in {
    s"""
       |1 + 1 =
       |${plus(1,1)}
     """.stripMargin should be("""
       |1 + 1 =
       |2
     """.stripMargin)
  }

  def plus(a: Int, b: Int) = a + b
}
