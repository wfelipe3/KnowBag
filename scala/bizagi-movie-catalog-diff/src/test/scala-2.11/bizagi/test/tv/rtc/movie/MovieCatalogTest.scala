package bizagi.test.tv.rtc.movie

import bizagi.test.tv.trc.movie._
import bizagi.test.tv.trc.movie.MoviesCatalog._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dev-williame on 10/31/16.
  */
class MovieCatalogTest extends FlatSpec with Matchers {

  object Movies {
    val matrix: Movie = Movie("matrix", "2000", "xysndf", "Action", "", 4.0, "Action")
    val martialArts = Movie("martial arts", "2000", "xysndf", "Action", "", 5.0, "Action")
    val kungfuPanda = Movie("kungfu panda", "2012", "iii89", "Comedy", "", 4.5, "Comedy")
  }

  val catalog = Catalog(Stream(
    Movies.matrix,
    Movies.martialArts
  ))

  behavior of "MoviesCatalog"

  it should "return all movies when term list is empty" in {
    searchByAnd(Seq())(catalog) should be(catalog)
  }

  it should "return at least one movie when the movie is in the catalog and the search is by title" in {
    searchBy(Title, "kungfu panda")(catalog) should be(Catalog(Stream()))
    searchBy(Title, "matrix")(catalog) should be(Catalog(Stream(Movies.matrix)))
  }

  it should "return values that match the term by title" in {
    searchBy(Title, "ma")(catalog) should be(Catalog(Stream(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that match the term by year" in {
    searchBy(Year, "1999")(catalog) should be(Catalog(Stream()))
    searchBy(Year, "2000")(catalog) should be(Catalog(Stream(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that match the term by genre" in {
    searchBy(Genre, "Drama")(catalog) should be(Catalog(Stream()))
    searchBy(Genre, "Action")(catalog) should be(Catalog(Stream(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that matches all terms" in {
    searchByAnd(Seq((Title, "matrix"), (Year, "2000")))(catalog) should be(Catalog(Stream(Movies.matrix)))
    searchByAnd(Seq((Year, "2000")))(catalog) should be(Catalog(Stream(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that matches all or terms" in {
    searchByOr(Seq((Title, "matrix"), (Year, "2012")))(Catalog(Stream(Movies.matrix, Movies.martialArts, Movies.kungfuPanda))) should be(Catalog(Stream(Movies.matrix, Movies.kungfuPanda)))
    searchByOr(Seq((Year, "2000")))(catalog) should be(Catalog(Stream(Movies.matrix, Movies.martialArts)))
  }

  it should "return values that matches and and or terms" in {
    val search = searchByOr(Seq((Title, "matrix"), (Year, "2012"))) _ andThen searchByAnd(Seq((Year, "2000")))
    search(Catalog(Stream(Movies.martialArts, Movies.matrix, Movies.kungfuPanda))) should be(Catalog(Stream(Movies.matrix)))
  }

  it should "return values sorted by rating" in {
    sortWith(MovieSort.rating)(catalog) should be(Catalog(Stream(Movies.martialArts, Movies.matrix)))
    val searchAndSort = (searchBy(Year, "2000") _).andThen(sortWith(MovieSort.rating))
    searchAndSort(catalog) should be(Catalog(Stream(Movies.martialArts, Movies.matrix)))
  }

  it should "allow to add a new Movie" in {
    add(Movies.kungfuPanda)(catalog) should be(Catalog(catalog.movies :+ Movies.kungfuPanda))
  }
}
