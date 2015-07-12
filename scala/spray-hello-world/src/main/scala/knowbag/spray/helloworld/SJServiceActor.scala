package knowbag.spray.helloworld

import akka.actor.{ActorRefFactory, Actor}
import spray.routing._
import spray.http._
import MediaTypes._
import spray.httpx.SprayJsonSupport._
import MyJsonProtocol._

/**
 * Created by feliperojas on 7/5/15.
 */
class SJServiceActor extends Actor with HttpService {
  def receive: Receive = runRoute(aSimpleRoute ~ anotherRoute)

  def actorRefFactory: ActorRefFactory = context

  val aSimpleRoute = {
    path("path1") {
      get {

        // Get the value of the content-header. Spray
        // provides multiple ways to do this.
        headerValue({
          case x@HttpHeaders.`Content-Type`(value) => Some(value)
          case default => None
        }) {
          // the header is passed in containing the content type
          // we match the header using a case statement, and depending
          // on the content type we return a specific object
          header => header match {

            // if we have this contentype we create a custom response
            case ContentType(MediaType("application/vnd.type.a"), _) => {
              respondWithMediaType(`application/json`) {
                complete {
                  Person("felipe", "typea", System.currentTimeMillis())
                }
              }
            }

            // if we have another content-type we return a different type.
            case ContentType(MediaType("application/vnd.type.b"), _) => {
              respondWithMediaType(`application/json`) {
                complete {
                  Person("felipe", "typeB", System.currentTimeMillis())
                }
              }
            }

            // if content-types do not match, return an error code
            case default => {
              complete {
                HttpResponse(406);
              }
            }
          }
        }
      }
    }
  }

  // handles the other path, we could also define these in separate files
  // This is just a simple route to explain the concept
  val anotherRoute = {
    path("path2") {
      get {
        // respond with text/html.
        respondWithMediaType(`text/html`) {
          complete {
            // respond with a set of HTML elements
            <html>
              <body>
                <h1>Path 2</h1>
              </body>
            </html>
          }
        }
      }
    }
  }
}
