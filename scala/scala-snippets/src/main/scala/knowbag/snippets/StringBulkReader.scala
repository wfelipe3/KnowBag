package knowbag.snippets

/**
 * Created by feliperojas on 7/04/15.
 */
class StringBulkReader(val source: String) extends BulkReader{
  override type In = String
  override def read: String = source
}
