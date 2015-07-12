
val x = new Rational(1,3)
val y = new Rational(5,7)
val z = new Rational(3,2)

x.add(y).toString
x.sub(y).toString

x.sub(y).sub(z)

class Rational(val x: Int, val y: Int) {
  def nom = x
  def denom = y

  def add(rational: Rational) = {
    new Rational(nom * rational.denom + rational.nom * denom,
      denom * rational.denom)
  }

  def neg = new Rational(-nom, denom)

  def sub(rational: Rational) = add(rational.neg)

  override def toString() = s"$nom/$denom"
}