package knowbag.anagram

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/23/15.
 */
class AnagramTest extends FlatSpec with Matchers {

  val defaultDictionary: Set[String] = Set("ay", "ya", "tac", "act", "picture", "piecrust", "cuprite")

  behavior of "anagram"

  it should "return empty list when the word is empty" in {
    anagramsForWordShouldBeExpected(word = "", expected = List.empty)
  }

  it should "return empty list when the word has only one letter" in {
    anagramsForWordShouldBeExpected(word = "a", expected = List.empty)
    anagramsForWordShouldBeExpected(word = "b", expected = List.empty)
    anagramsForWordShouldBeExpected(word = "z", expected = List.empty)
  }

  it should "return empty list when the word has only one letter many times" in {
    anagramsForWordShouldBeExpected(word = "aaaa", expected = List.empty)
    anagramsForWordShouldBeExpected(word = "cccccc", expected = List.empty)
  }

  it should "return empty list when the word is not valid" in {
    anagramsForWordShouldBeExpected(word = "acko", expected = List.empty)
  }

  it should "return list with value ya when the dictionary has the word ay and ya" in {
    anagramsForWordShouldBeExpected(word = "ay", expected = List("ya"))
  }

  it should "return empty list when the word is not in the dictionary" in {
    anagramsForWordShouldBeExpected(word = "cat", expected = List.empty)
  }

  it should "return anagram act when the word is cat" in {
    anagramsForWordShouldBeExpected(word = "tac", expected = List("act"))
  }

  it should "return anagrams with exact same letters than the word has" in {
    anagramsForWordShouldBeExpected(word = "picture", expected = List("cuprite"))
  }

  it should "return many values for remote dictionary" in {
    val dictionary = RemoteDictionaryProvider.getDictionary("http://codekata.com/data/wordlist.txt")
    anagramsForWordShouldBeExpected(word = "paste", expected = List("tapes", "peats", "septa", "tepas", "spate", "pates"), dictionary)
    anagramsForWordShouldBeExpected(word = "crepitus", expected = List("cuprites", "piecrust", "pictures"), dictionary)
    anagramsForWordShouldBeExpected(word = "parsley", expected = List("parleys", "players", "sparely", "replays"), dictionary)
  }

  def anagramsForWordShouldBeExpected(word: String, expected: List[String], dictionary: Set[String] = defaultDictionary): Unit = {
    Anagramator(dictionary).findAnagram(word) should be(expected)
  }
}
