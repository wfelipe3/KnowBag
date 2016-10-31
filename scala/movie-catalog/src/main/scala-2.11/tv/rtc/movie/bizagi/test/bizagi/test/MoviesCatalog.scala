package tv.rtc.movie.bizagi.test.bizagi.test

/**
  * Created by dev-williame on 10/26/16.
  */
object MoviesCatalog {

  def searchBy(terms: Seq[(Term, String)])(catalog: Catalog): Catalog = {
    val movies = terms.foldLeft(catalog.movies) { (movies, m) =>
      val (term, value) = m
      movies.filter(term.search(value))
    }
    Catalog(movies)
  }

  def searchBy(term: Term, value: String)(catalog: Catalog): Catalog =
    searchBy(Seq((term, value)))(catalog)

  def sortWith(sort: (Movie, Movie) => Boolean)(catalog: Catalog): Catalog =
    Catalog(catalog.movies.sortWith(sort))

  def add(movie: Movie)(catalog: Catalog): Catalog =
    Catalog(catalog.movies :+ movie)

}

object MovieSort {
  val rating: (Movie, Movie) => Boolean = (m1, m2) => m1.rating.compareTo(m2.rating) > 0
}

sealed trait Term {
  def search(value: String): Movie => Boolean
}

object Title extends Term {
  override def search(value: String): Movie => Boolean =
    m => m.title.toUpperCase().contains(value.toUpperCase())
}

object Year extends Term {
  override def search(value: String): (Movie) => Boolean =
    m => m.year.equals(value)
}

object Genre extends Term {
  override def search(value: String): (Movie) => Boolean =
    m => m.genre.equals(value)
}

case class Catalog(movies: Seq[Movie]) {
  def size = movies.size
}

case class Movie(title: String, year: String, imdbId: String, movieType: String, poster: String, rating: Double, genre: String)
