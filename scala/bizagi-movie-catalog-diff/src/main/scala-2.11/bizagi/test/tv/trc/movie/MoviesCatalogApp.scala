package bizagi.test.tv.trc.movie

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorRefFactory, ActorSystem, Props}
import akka.io.IO
import bizagi.test.tv.trc.movie.MoviesCatalog.{searchBy, sortWith}
import bizagi.test.tv.trc.movie.MoviesRepository.load
import net.liftweb.json.Extraction._
import net.liftweb.json.{DefaultFormats, _}
import spray.can.Http
import spray.routing.{HttpService, StandardRoute}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

/**
  * Created by dev-williame on 10/26/16.
  */
object MoviesCatalogApp extends App {
  implicit val system = ActorSystem("rtcMovies")
  val service = system.actorOf(Props[MoviesDiffActor])
  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)
}

class MoviesDiffActor extends Actor with HttpService {

  import context.dispatcher

  implicit val formats = DefaultFormats

  val blockingExecutionContext = {
    ExecutionContext.fromExecutor(Executors.newFixedThreadPool(200))
  }

  def actorRefFactory: ActorRefFactory = context
  override def receive: Receive = runRoute(route)

  lazy val catalog = load("/app/movies.json")

  val route =
    path("diff") {
      post {
        extract(_.request.entity.asString) { json =>
          completeAsync { () =>
            json
          }(blockingExecutionContext)
        }
      }
    } ~ path("movies") {
      get {
        parameters("title" ?, "year" ?, "genre" ?, "sort" ?) { (title, year, genre, sort) =>
          completeAsync { () =>
            val filterMovies = (searchBy(mapTerms((Title, title), (Year, year), (Genre, genre))) _).andThen(sortWith(sorterFrom(sort)))
            prettyRender(decompose(filterMovies(catalog)))
          }(blockingExecutionContext)
        }
      }
    }

  def completeAsync[A](f: () => String)(executionContext: ExecutionContextExecutor): StandardRoute = {
    complete {
      Future {
        f()
      }(executionContext)
    }
  }

  def mapTerms(termsOption: (Term, Option[String])*): Seq[(Term, String)] = {
    termsOption.foldRight(Seq.empty[(Term, String)]) { (t, acc) =>
      val (term, value) = t
      value.map(a => (term, a) +: acc).getOrElse(acc)
    }
  }

  def sorterFrom(orderOption: Option[String]): MovieSort.sort =
    orderOption.flatMap {
      case "rating" => Some(MovieSort.rating)
      case _ => None
    }.getOrElse(MovieSort.none)
}