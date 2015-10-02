package knowbag.finagle

import com.redis.RedisClient
import com.twitter.finagle.httpx.Methods.GET
import com.twitter.finagle.httpx.path.{Root, Path}
import com.twitter.finagle.{Httpx, Service, httpx}
import com.twitter.util.{Await, Future}

/**
 * Created by feliperojas on 9/13/15.
 */
object RestServer extends App {

  val redis = new RedisClient("192.168.59.103", 6379)

  val service = new Service[httpx.Request, httpx.Response] {
    def apply(req: httpx.Request): Future[httpx.Response] = {

      req.method -> Path(req.path) match {
        case httpx.Method.Get -> Root / "redis" => Future.value {
          val response = req.getResponse()
          response.setContentTypeJson()
          response.setContentString(s"""{"test":"${redis.get("test").getOrElse("default")}"}""")
          response
        }
      }
    }

  }

  val server = Httpx.serve(":8080", service)
  Await.ready(server)
}
