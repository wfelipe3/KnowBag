package knowbag.snippets

/**
 * Created by feliperojas on 7/04/15.
 */
abstract class BulkReader {

  type In
  val source: In
  def read: String
}
