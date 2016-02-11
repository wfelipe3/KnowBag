package knowbag.invitation

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 10/19/15.
 */
class NamesExtractorTest extends FlatSpec with Matchers {

  behavior of "Names extractor"

  it should "extract names from file" in {
    val names = NamesExtractor.extractFrom(file("/people.txt"))
    names should be(List("felipe rojas"))
  }

  //This method get the complete file path from resources folder
  private def file(file: String): String = {
    ResourcesFolderPathResolver.getResourcePath(file)
  }
}
