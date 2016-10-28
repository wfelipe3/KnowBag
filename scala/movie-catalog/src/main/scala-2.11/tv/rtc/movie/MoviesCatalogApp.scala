package tv.rtc.movie

/**
  * Created by dev-williame on 10/26/16.
  */
object MoviesCatalogApp extends App {
  val catalog = MoviesRepository.load("/Users/dev-williame/dev/knowbag/scala/movie-catalog/src/main/resources/movies.json")
  val addedMovie = MoviesCatalog.add(Movie("avengers", "2014", "8892fv", "movie", "http", 4.8, "Action"))(catalog)
  val sortedMovies = MoviesCatalog.sortWith(MovieSort.rating)(addedMovie)
  val filtered = MoviesCatalog.searchBy(Year, "2010")(sortedMovies)
  MoviesRepository.save("/Users/dev-williame/dev/knowbag/scala/movie-catalog/src/main/resources/movies2.json")(addedMovie)
}
