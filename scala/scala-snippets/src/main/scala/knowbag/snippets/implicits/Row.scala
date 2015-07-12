package knowbag.snippets.implicits

/**
 * Created by feliperojas on 6/4/15.
 */
trait Row {

  def getInt(colName: String): Int

  def getDouble(colName: String): Double

  def getText(colName: String): String
}

case class InvalidColumnName(name: String) extends RuntimeException(s"Invalid column name $name")
