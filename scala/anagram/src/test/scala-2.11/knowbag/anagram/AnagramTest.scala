package knowbag.anagram

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 7/23/15.
 */
class AnagramTest extends FlatSpec with Matchers {

  val defaultDictionary: Set[String] = Set("ay", "ya", "tac", "act", "picture", "piecrust", "cuprite")

  behavior of "anagram"

  it should "return empty list when the word is empty" in {
    anagramsShouldBe(word = "", expected = List.empty)
  }

  it should "return empty list when the word has only one letter" in {
    anagramsShouldBe(word = "a", expected = List.empty)
    anagramsShouldBe(word = "b", expected = List.empty)
    anagramsShouldBe(word = "z", expected = List.empty)
  }

  it should "return empty list when the word has only one letter many times" in {
    anagramsShouldBe(word = "aaaa", expected = List.empty)
    anagramsShouldBe(word = "cccccc", expected = List.empty)
  }

  it should "return empty list when the word is not valid" in {
    anagramsShouldBe(word = "acko", expected = List.empty)
  }

  it should "return list with value ya when the dictionary has the word ay and ya" in {
    anagramsShouldBe(word = "ay", expected = List("ya"))
  }

  it should "return empty list when the word is not in the dictionary" in {
    anagramsShouldBe(word = "cat", expected = List.empty)
  }

  it should "return anagram act when the word is cat" in {
    anagramsShouldBe(word = "tac", expected = List("act"))
  }

  it should "return anagrams with exact same letters than the word has" in {
    anagramsShouldBe(word = "picture", expected = List("cuprite"))
  }

  it should "return many values for remote dictionary" in {
    val dictionary = RemoteDictionaryProvider.getDictionary("http://codekata.com/data/wordlist.txt")
    anagramsShouldBe(word = "paste", expected = List("tapes", "peats", "septa", "tepas", "spate", "pates"), dictionary)
    anagramsShouldBe(word = "crepitus", expected = List("cuprites", "piecrust", "pictures"), dictionary)
    anagramsShouldBe(word = "parsley", expected = List("parleys", "players", "sparely", "replays"), dictionary)
  }

  def anagramsShouldBe(word: String, expected: List[String], dictionary: Set[String] = defaultDictionary): Unit = {
    Anagramator(dictionary).findAnagram(word) should be(expected)
  }
}
