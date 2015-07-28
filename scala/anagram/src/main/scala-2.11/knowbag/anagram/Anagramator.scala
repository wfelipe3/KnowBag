package knowbag.anagram

/**
 * Created by feliperojas on 7/23/15.
 */
case class Anagramator(dictionary: Set[String]) {

  def findAnagram(word: String): List[String] = {
    if (dictionaryHas(word))
      searchAnagramsFor(word)
    else
      List.empty
  }

  def searchAnagramsFor(word: String): List[String] = {
    dictionary
      .filter(_.length == word.length)
      .filter(isAnagramFor(word))
      .filterNot(_ == word)
      .toList
  }

  def isAnagramFor(word: String)(anagram: String): Boolean = {
    if (word.isEmpty)
      true
    else if (anagramDoesNotHaveLetter(anagram, word.head))
      false
    else
      isAnagramFor(word.tail)(removeLetter(word.head.toString, anagram))
  }

  def removeLetter(letter: String, anagram: String): String = {
    anagram.replaceFirst(letter, "")
  }

  def anagramDoesNotHaveLetter(anagram: String, letter: Char): Boolean = {
    !anagram.contains(letter)
  }

  def dictionaryHas(word: String): Boolean = {
    dictionary.contains(word)
  }
}
