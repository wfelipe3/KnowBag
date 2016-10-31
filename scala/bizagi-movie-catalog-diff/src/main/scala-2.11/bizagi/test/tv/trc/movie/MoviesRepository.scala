package bizagi.test.tv.trc.movie

import java.io.File
import java.nio.file.{Files, Paths}

import net.liftweb.json.Extraction._
import net.liftweb.json.{DefaultFormats, _}

/**
  * Created by dev-williame on 10/26/16.
  */
object MoviesRepository {

  implicit val formats = DefaultFormats

  def load(path: String): Catalog = {
    val fileContent = scala.io.Source.fromFile(new File(path))
    val json = parse(fileContent.getLines().mkString("\n"))
    val jsonT = json transformField {
      case JField("Movies", x) => JField("movies", x)
      case JField("Title", x) => JField("title", x)
      case JField("Year", x) => JField("year", x)
      case JField("Type", x) => JField("movieType", x)
      case JField("imdbID", x) => JField("imdbId", x)
      case JField("Poster", x) => JField("poster", x)
      case JField("Rating", x) => JField("rating", x)
      case JField("Genre", x) => JField("genre", x)
    }
    jsonT.extract[Catalog]
  }

  def save(path: String)(catalog: Catalog): Unit = {
    val json = decompose(catalog)
    val jsonT = json transformField {
      case JField("movies", x) => JField("Movies", x)
      case JField("title", x) => JField("Title", x)
      case JField("year", x) => JField("Year", x)
      case JField("movieType", x) => JField("Type", x)
      case JField("imdbId", x) => JField("imdbID", x)
      case JField("poster", x) => JField("Poster", x)
      case JField("rating", x) => JField("Rating", x)
      case JField("genre", x) => JField("Genre", x)
    }
    Files.write(Paths.get(path), prettyRender(jsonT).getBytes)
  }

}
