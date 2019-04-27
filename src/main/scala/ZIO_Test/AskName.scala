package ZIO_Test

import ZIO_Test.ConsoleDSL._
import cats.data.Reader
import scalaz.zio.ZIO

object AskName {

  val askNameAndPrintReply =
    for {
      _ <- printLn("Tell me your name!")
      n <- readLn()
      _ <- printLn(s"Hi! Your name seems to be ${n}")
    } yield ()
}

object PrintNameReceived {

  val getNamePrintAndReturnWelcomeHtml = Reader[UserData, ZIO[Console, Nothing, String]] {
    userData =>
    for {
      _ <- printLn(s"Name Received ${userData.name} your authenticated with ${userData.userName}")
    } yield s"<html><body>Hi ${userData.name} authenticate ${userData.userName}</body></html>"
  }
}
