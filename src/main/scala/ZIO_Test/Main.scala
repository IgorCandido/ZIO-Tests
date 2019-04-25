package ZIO_Test

import scalaz.zio.{DefaultRuntime, ZIO}
import ConsoleDSL._



object Main extends App {
  val runtime = new DefaultRuntime {}

  val program =
    for {
      _ <- printLn("Tell me your name!")
      n <- readLn()
      _ <- printLn(s"Hi! Your name seems to be ${n}")
    } yield ()

  runtime.unsafeRun(program.provide(LiveConsole))
}
