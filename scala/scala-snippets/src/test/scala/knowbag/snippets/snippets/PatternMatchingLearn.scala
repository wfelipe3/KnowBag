package knowbag.snippets.snippets

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 23/04/15.
 */
class PatternMatchingLearn extends FlatSpec with Matchers {

  behavior of "pattern matching"

  it should "match simple values" in {
    val coins = Seq(true, false)
    val values = for (coin <- coins)
    yield coin match {
        case true => "got tails"
        case false => "got heads"
      }

    values should be(List("got tails", "got heads"))
  }

  it should "be able to create a default match for every value" in {
    val values = for {
      value <- Seq(1, 2.3, "one", 2, true, 'four)
    } yield value match {
        case 1 => "int 1"
        case i: Int => "other int"
        case d: Double => "a double"
        case "one" => "string one"
        case s: String => s"other string $s"
        case unexpected => s"unexpected value $unexpected"
      }

    val valuesWithWildCard = for {
      value <- Seq(1, 2.3, "one", 2, true, 'four)
    } yield value match {
        case 1 => "int 1"
        case _: Int => "other int"
        case _: Double => "a double"
        case "one" => "string one"
        case _: String => s"other string $value"
        case _ => s"unexpected value $value"
      }

    values should be(List("int 1", "a double", "string one", "other int", "unexpected value true", "unexpected value 'four"))
    valuesWithWildCard should be(List("int 1", "a double", "string one", "other int", "unexpected value true", "unexpected value 'four"))
  }

  it should "be eager when matches are apply" in {
    val value = 10
    val matches = value match {
      case _: Any => "any"
      case _: Int => "int"
    }
    matches should be("any")
  }

  it should "use batics when a parameter value is pass to match" in {
    def matchesValue(y: Int): List[Boolean] = {
      val values = List(99, 100, 101)
      values.map {
        case `y` => true
        case _ => false
      }
    }

    matchesValue(100) should be(List(false, true, false))
  }

  it should "be able to use or operand in match clauses" in {
    val values = List(1, 2.3, "true", false)
    values.map { value =>
      value match {
        case _: Int | _: Double => s"number $value"
        case _: String => s"string $value"
        case _: Boolean => s"boolean $value"
        case _ => s"unexpected $value"
      }
    } should be(List("number 1", "number 2.3", "string true", "boolean false"))
  }

  it should "be able to match sequences" in {
    val nonEmptySeq = Seq(1, 2, 3, 4, 5, 6)
    val emptySeq = Seq.empty[Int]

    val nonEmptyList = List(1, 2, 3, 4, 5, 6, 7)
    val emptyList = Nil

    val nonEmptyVector = Vector(1, 2, 3, 4, 5, 6, 6, 7)
    val emptyVector = Vector.empty[Int]

    val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)
    val emptyMap = Map.empty[String, Int]

    def seqToString[T](seq: Seq[T]): String = {
      seq match {
        case head +: tail => s"$head +: ${seqToString(tail)}"
        case Nil => "Nil"
      }
    }

    def printSeq[T](seqs: Seq[T]*): Unit = {
      seqs.foreach(value => println(seqToString(value)))
    }

    printSeq(nonEmptySeq, emptySeq, nonEmptyList, emptyList, nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq)
  }

  it should "match pairs" in {
    val pairs = Seq(("Scala", "martin"), ("Clojure", "Rick"), ("Lisp", "John"))

    pairs.map(tuple => tuple match {
      case ("Scala", _) => "found"
      case (language, name) => s"$language $name"
    }) should be(List("found", "Clojure Rick", "Lisp John"))
  }

  it should "be possible to add logic to matches" in {
    val numbers = List(1, 2, 3, 4)
    val even = for (n <- numbers) yield n match {
      case _ if n % 2 == 0 => s"even: $n"
      case _ => s"odd: $n"
    }

    even should be(List("odd: 1", "even: 2", "odd: 3", "even: 4"))
  }

  it should "match case classes with their types" in {
    case class Address(street: String, city: String, country: String)
    case class Person(name: String, age: Int, address: Address)

    val felipe = Person(name = "felipe", age = 27, address = Address(street = "Java av", city = "Bogota", country = "colombia"))
    val maria = Person(name = "maria", age = 26, address = Address(street = "villa luz av", city = "Bogota", country = "cancun"))
    val romina = Person(name = "romina", age = 1, address = Address(street = "Perro av", city = "Bogota", country = "perro pais"))

    for (person <- Seq(felipe, maria, romina)) {
      person match {
        case Person("felipe", 27, Address(_, "Bogota", _)) => println("hi felipe")
        case Person("maria", 26, Address("villa luz av", "Bogota", "cancun")) => println("hi maria")
        case Person(name, age, _) => println(s"Who are you $name with age $age")
      }
    }
  }

  it should "match tuples with their types" in {
    val itemsCosts = Seq(("Pencil", 0.52), ("Paper", 1.35), ("Notebook", 2.43))
    val itemsCostsIndices = itemsCosts.zipWithIndex
    for (itemCostIndex <- itemsCostsIndices) {
      itemCostIndex match {
        case ((item, cost), index) => println(s"$index $item costs $cost each")
      }
    }
  }

  it should "match regular expressions" in {
    val bookExtractor = """Book: title=([^,]+),\s+author=(.+)""".r
    val magazineExtractor = """Magazine: title=([^,]+),\s+issue=(.+)""".r

    val catalog = Seq(
      "Book: title=programming scala second edition, author=Dean wampler",
      "Magazine: title=The new Yorker, issue=January 2014",
      "unknown: text=Who put this here"
    )

    for (item <- catalog) {
      item match {
        case bookExtractor(title, author) =>
          println(s"book $title written by author $author")
        case magazineExtractor(title, issue) =>
          println(s"magazine $title with issue $issue")
        case entry => println(s"unknown $entry")
      }
    }
  }
}
