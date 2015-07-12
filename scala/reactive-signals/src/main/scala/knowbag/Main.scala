package knowbag

import knowbag.frp.Var

/**
 * Created by feliperojas on 6/2/15.
 */
object Main extends App {

  val a = Var(0)
  val b = Var(a() + 1)

  println(b())
  a() = 5
  println(b())
  a() = 6
  println(b())
}
