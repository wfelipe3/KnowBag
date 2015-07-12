package knowbag.random_generator

import org.scalatest.{Matchers, FlatSpec}

import scala.util.Random

/**
 * Created by feliperojas on 25/04/15.
 */
class RandomGeneratorTest extends FlatSpec with Matchers {

  "Int random generator" should "generate a random int" in {
    val isInt = getIntGenerator.generate match {
      case _: Int => true
    }

    isInt should be(true)
  }

  "Boolean random generator" should "generate a random boolean" in {
    val generator = new RandomGenerator[Boolean] {
      def generate: Boolean = getIntGenerator.generate > 0
    }

    val isBoolean = generator.generate match {
      case _: Boolean => true
    }

    isBoolean should be(true)
  }

  def getIntGenerator: RandomGenerator[Int] = {
    new RandomGenerator[Int] {
      val random = new Random

      def generate: Int = random.nextInt()
    }
  }

  "Map can be used to generate values" should "pass a function to transform generated value to other value" in {
    val intGenerator = getIntGenerator
    val booleanGenerator = intGenerator.map(value => value > 0)
    val pairGenerator = intGenerator.flatMap(x => intGenerator.map(y => (x, y)))
    val other = intGenerator.flatMap(x => intGenerator.flatMap(y => intGenerator.map(z => (x, y ,z))))
    val other2 = intGenerator.map(x => intGenerator.map(y => intGenerator.map(z => (x, y ,z))))

    println(other.generate)
    println(other2.generate.generate.generate)
  }
}
