package knowbag.phonenumbers

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/11/15.
 */
class PhoneNumbersToWordsConverterTest extends FlatSpec with Matchers {

  "convert words to phone numbers" should "take a number and convert it to the respective phone number" in {

    val words = List("Java", "Kata", "Lava", "nata", "scala", "is", "fun", "PAAJAGPDTM")

    val mnem = Map('2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
      '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

    val charCode = for {
      (number, letters) <- mnem
      letter <- letters
    } yield letter -> number

    def wordCode(word: String) =
      word.toUpperCase.map(charCode)

    val wordsForNum: Map[String, Seq[String]] =
      words groupBy wordCode withDefaultValue Seq()

    def createWord(numbers: String) = {
      for {
        value <- numbers
      } yield mnem(value).charAt(0)
    }.mkString("")

    def encode(number: String): Set[List[String]] =
      if (number.isEmpty) Set(List())
      else {
        for {
          split <- 1 to number.length
          word <- wordsForNum(number.take(split))
          rest <- encode(number.drop(split))
        } yield word :: rest
      }.toSet

    val number: String = "7225247386"

    val encoded =
      (1 to number.length).flatMap(split => for {
        word <- wordsForNum(number.take(split))
        rest <- encode(number.drop(split))
      } yield word :: rest).toSet

    val encoded2 =
      (1 to number.length).flatMap(split =>
        wordsForNum(number.take(split)).flatMap(word => for {
          rest <- encode(number.drop(split))
        } yield word :: rest)
      ).toSet

    val encoded3 =
      (1 to number.length).flatMap(split =>
        wordsForNum(number.take(split)).flatMap(word =>
          encode(number.drop(split)).map(rest => word :: rest)
        )
      ).toSet

    println(s"encoded value $encoded")
    println(s"encoded2 value $encoded2")
    println(s"encoded3 value $encoded3")

    wordCode("Java") should be("5282")
    println(wordsForNum)
    println(encode(number))
    println(createWord(number))
  }

}