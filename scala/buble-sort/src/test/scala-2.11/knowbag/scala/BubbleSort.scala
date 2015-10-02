package knowbag.scala

/**
 * Created by feliperojas on 10/1/15.
 */
object BubbleSort {

  def sort(list: Seq[Int]): Seq[Int] =
    if (list.size > 1)
      (list.size to 2 by -1).foldLeft(list) { (l, i) =>
        sort(l, pivot = 0, index = 1, n = i)
      }
    else list

  private def sort(list: Seq[Int], pivot: Int, index: Int, n: Int): Seq[Int] =
    if (index == n)
      list
    else if (list(pivot) > list(index))
      sort(swap(list, index, pivot), pivot = index, index = index + 1, n)
    else
      sort(list, pivot = index, index = index + 1, n)

  private def swap(list: Seq[Int], rightIndex: Int, leftIndex: Int) =
    list.updated(rightIndex, list(leftIndex)).updated(leftIndex, list(rightIndex))
}
