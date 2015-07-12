package knowbag.snippets.snippets

import java.io.File

import knowbag.snippets.{StringBulkReader, FileBulkReader}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 7/04/15.
 */
class TypeMemberLearn extends FlatSpec with Matchers {

  "Type member works with type inside abstract class and " should "parametrize the type of the class" in {
    new StringBulkReader("test").read should be("test")
    new FileBulkReader(new File("/Users/feliperojas/KnowBag/scala/scala-snippets/src/test/resources/test.txt")).read should be("This is a test")
  }
}
