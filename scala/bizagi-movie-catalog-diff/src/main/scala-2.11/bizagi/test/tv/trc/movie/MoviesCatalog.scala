package bizagi.test.tv.trc.movie

import bizagi.test.tv.trc.movie.MoviesCatalog.Search

/**
  * Created by dev-williame on 10/31/16.
  */
object MoviesCatalog {

  type Search = Movie => Boolean

  def searchByAnd(terms: Seq[(Term, String)])(catalog: Catalog): Catalog = {
    val and = join(terms, And)
    Catalog(catalog.movies.filter(and))
  }

  def searchByOr(terms: Seq[(Term, String)])(catalog: Catalog): Catalog = {
    val or = join(terms, Or)
    Catalog(catalog.movies.filter(or))
  }

  def join(terms: Seq[(Term, String)], termMonoid: TermMonoid): Search = {
    terms.foldLeft(termMonoid.zero) { (s, t) =>
      termMonoid.op(s, t._1.search(t._2))
    }
  }

  def searchBy(term: Term, value: String)(catalog: Catalog): Catalog =
    searchByAnd(Seq((term, value)))(catalog)

  def sortWith(sort: (Movie, Movie) => Boolean)(catalog: Catalog): Catalog =
    Catalog(catalog.movies.sortWith(sort))

  def add(movie: Movie)(catalog: Catalog): Catalog =
    Catalog(catalog.movies :+ movie)
}

object MovieSort {
  type sort = (Movie, Movie) => Boolean
  val rating: sort = (m1, m2) => m1.rating.compareTo(m2.rating) > 0
  val none: sort = (_, _) => false
}

trait TermMonoid {
  val zero: Search
  def op(m1: Search, m2: Search): Search
}

object And extends TermMonoid {
  val zero: Search = _ => true
  def op(m1: Search, m2: Search): Search =
    m => {
      m1(m) && m2(m)
    }
}

object Or extends TermMonoid {
  val zero: Search = _ => false
  def op(m1: Search, m2: Search): Search =
    m => {
      m1(m) || m2(m)
    }
}

sealed trait Term {
  def search(value: String): Search
}

object Title extends Term {
  override def search(value: String): Search =
    m => m.title.toUpperCase().contains(value.toUpperCase())
}

object Year extends Term {
  override def search(value: String): Search =
    m => m.year.equals(value)
}

object Genre extends Term {
  override def search(value: String): Search =
    m => m.genre.equals(value)
}

case class Catalog(movies: Stream[Movie]) {
  def size: Int = movies.size
}

case class Movie(title: String, year: String, imdbId: String, movieType: String, poster: String, rating: Double, genre: String, token: String = "")