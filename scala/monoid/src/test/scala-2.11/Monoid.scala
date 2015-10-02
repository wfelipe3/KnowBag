/**
 * Created by feliperojas on 8/31/15.
 */
trait Monoid[A] {

  def op(a1: A, a2: A): A

  def zero: A

}
