package tv.rtc.movie

import java.util.concurrent.Executors

import org.scalatest.{FlatSpec, Matchers}
import tv.rtc.movie.MoviesCatalog.{searchBy, sortWith}

/**
  * Created by dev-williame on 10/26/16.
  */
class MoviesCatalogTest extends FlatSpec with Matchers {

  object Movies {
    val matrix: Movie = Movie("matrix", "2000", "xysndf", "Action", "", 4.0, "Action")
    val martialArts = Movie("martial arts", "2000", "xysndf", "Action", "", 5.0, "Action")
    val kunfuPanda = Movie("kunfu panda", "2012", "iii89", "Comedy", "", 4.5, "Comedy")
  }

  val catalog = Catalog(Seq(
    Movies.matrix,
    Movies.martialArts
  ))

  behavior of "MoviesCatalog"

  it should "return at least one movie when the movie is in the catalog and the search is by title" in {
    searchBy(Title, "kunfu panda")(catalog) should be(Catalog(Seq()))
    searchBy(Title, "matrix")(catalog) should be(Catalog(Seq(Movies.matrix)))
  }

  it should "return values that match the term by title" in {
    searchBy(Title, "ma")(catalog) should be(Catalog(Seq(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that match the term by year" in {
    searchBy(Year, "1999")(catalog) should be(Catalog(Seq()))
    searchBy(Year, "2000")(catalog) should be(Catalog(Seq(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that match the term by genre" in {
    searchBy(Genre, "Drama")(catalog) should be(Catalog(Seq()))
    searchBy(Genre, "Action")(catalog) should be(Catalog(Seq(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that matches all terms" in {
    MoviesCatalog.searchBy(Seq((Title, "matrix"), (Year, "2000")))(catalog) should be(Catalog(Seq(Movies.matrix)))
    MoviesCatalog.searchBy(Seq((Year, "2000")))(catalog) should be(Catalog(Seq(Movies.matrix, Movies.martialArts)))
  }

  it should "return values sorted by rating" in {
    sortWith(MovieSort.rating)(catalog) should be(Catalog(Seq(Movies.martialArts, Movies.matrix)))
    val searchAndSort = (searchBy(Year, "2000") _).andThen(sortWith(MovieSort.rating))
    searchAndSort(catalog) should be(Catalog(Seq(Movies.martialArts, Movies.matrix)))
  }

  it should "allow to add a new Movie" in {
    MoviesCatalog.add(Movies.kunfuPanda)(catalog) should be(Catalog(catalog.movies :+ Movies.kunfuPanda))
  }
}
