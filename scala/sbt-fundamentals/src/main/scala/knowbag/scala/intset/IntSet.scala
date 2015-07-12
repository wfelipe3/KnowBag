package knowbag.scala.intset

/**
 * Created by feliperojas on 26/02/15.
 */
trait IntSet {
  def contains(x: Int): Boolean
  def include(x: Int): IntSet
  def union(other: IntSet): IntSet
}
