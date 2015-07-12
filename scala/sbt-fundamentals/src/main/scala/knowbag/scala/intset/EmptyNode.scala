package knowbag.scala.intset

/**
 * Created by feliperojas on 26/02/15.
 */
object EmptyNode extends IntSet {

  def contains(x: Int): Boolean = false
  def include(x: Int): IntSet = ValueNode.newNode(x)
  def union(other: IntSet): IntSet = other

  override def toString = "."

}
