package knowbag.scala.functional

/**
 * Created by feliperojas on 17/02/15.
 */
object HitCounter {

  private var count = 0

  def add(): Unit = {
    count = count + 1
  }

  def getCount = count
}
