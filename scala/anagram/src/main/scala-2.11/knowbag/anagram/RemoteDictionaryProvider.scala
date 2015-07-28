package knowbag.anagram

import scala.io.Source.fromURL

/**
 * Created by feliperojas on 7/23/15.
 */
object RemoteDictionaryProvider {

  def getDictionary(url: String): Set[String] =
    fromURL(url, "ISO-8859-1").getLines().toSet
}
