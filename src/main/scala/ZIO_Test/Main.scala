package ZIO_Test

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.Credentials
import akka.stream.ActorMaterializer
import scalaz.zio.DefaultRuntime

object ReadAndWriteConsole {
  val runtime = new DefaultRuntime {}

  runtime.unsafeRun(AskName.askNameAndPrintReply.provide(LiveConsole))
}

object Main extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val runtime = new DefaultRuntime {}

  def auth(credentials: Credentials): Option[String] =
    credentials match {
      case p@Credentials.Provided(id) if id == "testUser" && p.verify("testPassword") => Some(id)
      case _ => None
    }

  val route =
    pathPrefix("hello" / Segment) { name =>
      pathEnd {
        authenticateBasic(realm = "ziotest", auth) { username =>
          get {
            val printReceived =
              PrintNameReceived
                .getNamePrintAndReturnWelcomeHtml
                .provide(new AkkaHTTPReaderFromRequest(_userName = username, _name = name) with LiveConsole)
            complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, runtime.unsafeRun(printReceived))))
          }
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", port = 8080)

  println("Running")
  /*  StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate()) */
}
