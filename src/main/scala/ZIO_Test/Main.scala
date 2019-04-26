package ZIO_Test

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scalaz.zio.DefaultRuntime

import scala.io.StdIn

object ReadAndWriteConsole {
  val runtime = new DefaultRuntime {}

  runtime.unsafeRun(AskName.askNameAndPrintReply.provide(LiveConsole))
}

object Main extends App {
  type ReaderWithConsole = AkkaHTTPReaderFromRequest with LiveConsole

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  val runtime = new DefaultRuntime {}

  val route =
    pathPrefix("hello" / Segment) { name =>
      pathEnd {
        get {
          val printReceived =
            PrintNameReceived
              .getNamePrintAndReturnWelcomeHtml
              .provide(new AkkaHTTPReaderFromRequest(name) with LiveConsole)
          complete(HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, runtime.unsafeRun(printReceived))))
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", port = 8080)

  println("Running")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
