package knowbag

import org.scalatest.{Matchers, FlatSpec}
import com.kolor.docker.api._
import com.kolor.docker.api.json.Formats._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
 * Created by feliperojas on 10/9/15.
 */
class DockerTest extends FlatSpec with Matchers {

  implicit val docker = Docker("192.168.99.100", 2376)

  behavior of "docker"

  it should "ping docker" in {
    val timeout = Duration.create(20, "second")
    val result = Await.result(docker.images(), timeout)
    result.foreach(i => println(i))
  }
}
