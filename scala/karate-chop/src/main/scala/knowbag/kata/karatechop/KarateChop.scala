package knowbag.kata.karatechop

/**
 * Created by feliperojas on 16/03/15.
 */
object KarateChop {
  def chop(value: Int, values: List[Int]): Int = {
    chopAcc(value, getListOrThrowException(values), 0)
  }

  private def getListOrThrowException(values: List[Int]): List[Int] = {<
    return Option(values).getOrElse(throwNoSuchElementException)
  }

  private def chopAcc(t: Int, tail: List[Int], index: Int): Int = {
    if (tail.isEmpty)
      throwNoSuchElementException
    else
      if (tail.head == t)
        index
      else if (t < tail.head)
        chopAcc(t, tail.slice(from = 0, until = tail.size - index / 2), index / 2)
      else
        chopAcc(t, tail.slice(from = index, until = tail.size), (index / 2) + index)
  }

  private def throwNoSuchElementException: Nothing = {
    throw new scala.NoSuchElementException
  }
}
