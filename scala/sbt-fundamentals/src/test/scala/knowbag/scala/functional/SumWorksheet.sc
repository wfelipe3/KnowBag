
def moreProduct(f: Int => Int): (Int, Int) => Int = {
  def inner(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * f(a + 1)
  }
  inner
}

moreProduct(x => x * x)(3, 4)

def prod = moreProduct(x => x * x)
prod(3, 4)

def product(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 1 else f(a) * product(f)(a + 1, b)
}

def otherProd = product(x => x * x)(_, _)
otherProd(3, 4)


product(value => value * value)(3, 4)
def fact(n: Int) = product(value => value)(1, n)

fact(5)


def general(f: Int => Int)(a: Int, b: Int)(c: Int, g: (Int, Int) => Int): Int = {
  if (a > b) c else g(f(a), general(f)(a + 1, b)(c, g))
}

general(value => value * value)(3, 4)(1, (x, y) => x * y)


def mapReduce(f: Int => Int, g: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  if (a > b) zero
  else g(f(a), mapReduce(f, g, zero)(a + 1, b))
}

def otherProduct(f: Int => Int)(a: Int, b: Int) = mapReduce(f, (x, y) => x * y, 1)(a, b)
otherProduct(value => value * value)(3, 4)


