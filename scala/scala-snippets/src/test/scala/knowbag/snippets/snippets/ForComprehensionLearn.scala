package knowbag.snippets.snippets

import java.nio.file.{Paths, Path}

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by feliperojas on 8/04/15.
 */
class ForComprehensionLearn extends FlatSpec with Matchers {

  "for can be used to iterate throw a collection and" should "work to traverse a collection" in {
    val cities = List("bogota", "medellin", "Cali")
    for (city <- cities) println(city)
  }

  "traditional for" should "be created with an initial value" in {
    for (i <- 1 to 10) println(i)
  }

  "for can have filters and" should "be applied to every element to the collection" in {
    val cities = List("bogota", "medellin", "Cali")
    for (city <- cities
         if city != "bogota"
         if city == "medellin"
    ) println(city)
  }

  "for can yield other collections" should "return a new collection with travesed values" in {
    val cities: List[String] = getCities

    val coffeeRegions = for {
      city <- cities
      if city == "Armenia" || city == "Pereira"
    } yield city

    coffeeRegions should be(List("Armenia", "Pereira"))
  }

  "for can have expression and they " should "be val values" in {
    val upperCities = for {
      city <- getCities
      upperCity = city.toUpperCase
    } yield upperCity

    upperCities should be(getCities.map(city => city.toUpperCase))
  }

  "for with Option" should "be traversed like a regular collection" in {
    val optionCities: List[Option[String]] = getCities.map(Some(_))

    val upperCities = for {
      cityOption <- optionCities
      city <- cityOption
      uppered = city.toUpperCase
    } yield uppered

    val upperCitiesWithOption = for {
      Some(city) <- optionCities
      uppered = city.toUpperCase
    } yield uppered

    upperCities should be(upperCitiesWithOption)
  }

  def getCities: List[String] = {
    List("bogota", "medellin", "Cali", "Armenia", "Pereira")
  }
}
