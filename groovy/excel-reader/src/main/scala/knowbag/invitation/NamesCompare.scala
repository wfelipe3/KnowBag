package knowbag.invitation

/**
 * Created by feliperojas on 10/19/15.
 */
object NamesCompare {
  def compare(l1: Seq[String], l2: Seq[String]): Seq[String] = {
    def contains(l1: Seq[String], l2: Seq[String], notInBoth: Seq[String]): Seq[String] = {
      if(l1 isEmpty)
        notInBoth
      else if(containsName(l2, l1.head))
        contains(l1.tail, l2, notInBoth)
      else
        contains(l1.tail, l2, l1.head +: notInBoth)
    }
    contains(l1, l2, Nil)
  }

  def containsName(l2: Seq[String], startName: String) =
    l2.exists(name => removeWhiteSpaces(name).equalsIgnoreCase(removeWhiteSpaces(startName)))

  def removeWhiteSpaces(name: String): String = {
    name.replaceAll("\\s+", "")
  }
}
