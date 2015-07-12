package knowbag.scala.intset

/**
 * Created by feliperojas on 26/02/15.
 */
class ValueNode(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains  x
    else if (x > elem) right contains x
    else true

  def include(x: Int): IntSet =
    if (x < elem) new ValueNode(elem, left include x, right)
    else if (x > elem) new ValueNode(elem, left, right include x)
    else this

  def union(other: IntSet): IntSet =
    ((left union right) union other) include elem

  override def toString = s"{$left$elem$right}"

}

object ValueNode {
  def newNode(elem: Int) = new ValueNode(elem, EmptyNode, EmptyNode)
}