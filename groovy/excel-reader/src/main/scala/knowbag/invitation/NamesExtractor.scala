package knowbag.invitation

import scala.io.Source
/**
 * Created by feliperojas on 10/19/15.
 */
object NamesExtractor {
  
  def extractFrom(file: String): List[String] = {
    def getLines: Iterator[String] = {
      Source.fromFile(file).getLines()
    }
    def toList(lines: Iterator[String]): List[String] = {
      lines.foldLeft(List.empty[String])((values, value) => value.trim :: values)
    }
    toList(getLines)
  }
}
