package knowbag.scala.functional

/**
 * Created by feliperojas on 22/02/15.
 */
class Rational(val x: Int, val y: Int) {

  def nom = x

  def denom = y

  def add(rational: Rational) = {
    new Rational(nom * rational.denom + rational.nom * denom,
      denom * rational.denom)
  }
}
