package knowbag.scala.intset

/**
 * Created by feliperojas on 26/02/15.
 */
object Main extends App{

  val n5 = ValueNode.newNode(5)
  val n3 = n5.include(3)
  val n7 = ValueNode.newNode(7)
  println(n3.union(n7))

}
