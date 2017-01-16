/**
  * Created by dev-williame on 1/12/17.
  */
object Main extends App {

  def sum(x: Int) = x + x
  def multiply(x: Int) = x * x
  def toString(x: Int) = s"value: $x"

  def sumTwo(x: Int, y: Int) = x + y

  val curriedSumTwo = (x: Int) => (y: Int) => x + y

  val sumAndMultiply = (sum _).andThen(multiply)
  val sumTwoAndMultiply = (sumTwo _).curried(1).andThen(multiply)
  val sum1With= curriedSumTwo(1)
  val sum2With= curriedSumTwo(2)

  println(sumAndMultiply(1))
  println(sumTwoAndMultiply(2))
  print(sum1With(2))

  val sumString = (sum _).andThen(toString)
  val sumString2 = (toString _).compose(sum)

  val x: Int => String = x => x.toString

  toString(sum(2))

  println(sumString(2))

}
