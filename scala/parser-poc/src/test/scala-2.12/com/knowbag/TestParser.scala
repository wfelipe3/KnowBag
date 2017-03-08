package com.knowbag

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.scalatest.{FlatSpec, Matchers}

import scala.util.parsing.combinator._

/**
  * Created by dev-williame on 2/2/17.
  */
class TestParser extends FlatSpec with Matchers {

  "parse hello world" should "return simple value" in {
    object SimpleParser extends RegexParsers {
      def word: Parser[String] =
        """[a-z]+""".r ^^ (_.toString)
    }

    SimpleParser.parse(SimpleParser.word, "this is a test").get should be("this")
  }

  "parse complex" should "parse composed regex" in {
    case class WordFreq(word: String, count: Int)
    class SimpleParser extends RegexParsers {
      def word: Parser[String] = """[a-z]+""".r ^^ (_.toString)
      def number: Parser[Int] = """(0|[1-9]\d*)""".r ^^ (_.toInt)
      def freq: Parser[WordFreq] = word ~ number ^^ { case wd ~ fr => WordFreq(wd, fr) }
      def test: Parser[String] = "\\[#*\\s*\\]".r ^^ (s => s)
      def parseOpen: Parser[String] = "\\[".r ^^ (s => s)
      def parseSharp: Parser[String] = "#*".r ^^ (s => s)
      def boundary: Parser[String] =
        """================================================================================""".r
          .^^(s => s)
      val header: Parser[Seq[String]] =
        ("[^=].*".r ^^ (s => s)) *
      def errors: Parser[(Seq[String], String)] = {

        val start: Parser[Unit] = ">".r ^^ (_ => ())
        val name: Parser[String] = ".+\\s{6}".r ^^ (s => s)
        val name2: Parser[String] = "^(> .+)".r ^^ (s => s)
        val int: Parser[Int] = "\\d+\\s".r ^^ (_.trim.toInt)
        val percentage: Parser[Double] = "\\(\\d+\\.?\\d+%\\)".r ^^ (s => s.replace("(", "").replace("%)", "").toDouble)

        val row = start ~ name ~ int ~ percentage ~ (name2 *) ^^ { case _ ~ n1 ~ i ~ p ~ n2 => s"$n1$n2$i$p" }
        val rows = row +

        header ~ boundary ^^ { case h ~ b => (h, b) }
      }
    }

    val parser = new SimpleParser
    parser.parse(parser.freq, "felipe   123").get should be(WordFreq("felipe", 123))
    parser.parse(parser.freq,
      """
        |this
        |123
      """.stripMargin).get should be(WordFreq("this", 123))

    val repetition = parser.freq.*

    parser.parse(parser.freq, "felipe rojas").isEmpty should be(true)

    println(parser.parse(repetition, "felipe 123 gomez 124"))

    println(LocalDateTime.parse("2017-02-03 22:10:55", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))

    println(parser.parse(parser.parseOpen, "["))
    println(parser.parse(parser.parseSharp, "###"))
    println(parser.parse(parser.test, "[########################################################                  ] 76%"))
    println(parser.parse(parser.header,
      """
        |test
        |================================================================================""".stripMargin))
    println(parser.parse(parser.errors,
      """
        |---- Errors --------------------------------------------------------------------
        |> j.n.ConnectException: Connection refused: localhost/0:0:0:1:0:      5 (62.50%)
        |test
        |> j.n.ConnectException: Connection refused: localhost/0:0:0:2:0:     18 (45.00%)
        |================================================================================      """.stripMargin))
  }
}
