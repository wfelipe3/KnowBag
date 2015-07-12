package knowbag.scala.inset

import knowbag.scala.intset.{EmptyNode, ValueNode}
import org.scalatest.FunSuite

/**
 * Created by feliperojas on 26/02/15.
 */
class IntSetTest extends FunSuite {

  test("test") {
    val node = ValueNode.newNode(3)
    val otherNode = node.include(4)
    print(node)
    print(otherNode)
  }
}
