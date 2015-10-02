package dojos.bz.math.interpreter

/**
 * Created by feliperojas on 9/8/15.
 */
object PrefixProcessor {

  def eval(exp: String): Int = {
    val tokens = exp.split(" ")
    if ("(" == tokens.head) {
      eval(getSubExpression(tokens.tail, ""))
    } else {
      val operator = exp.head
      tokens.tail.map(_.toInt).reduceLeft(operationFor(operator))
    }
  }

  private def getSubExpression(tokens: Array[String], subExp: String): String = {
    if (tokens.head == ")") subExp
    else getSubExpression(tokens.tail, subExp + " " + tokens.head)
  }

  private def operationFor(operator: Char) = operator match {
    case '+' => (a: Int, b: Int) => a + b
    case '-' => (a: Int, b: Int) => a - b
    case '*' => (a: Int, b: Int) => a * b
    case '/' => (a: Int, b: Int) => a / b
  }

}
