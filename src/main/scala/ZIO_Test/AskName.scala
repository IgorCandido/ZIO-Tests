package ZIO_Test

import ZIO_Test.ConsoleDSL.{printLn, readLn}
import ZIO_Test.ReaderFromRequestDSL.{getName}

object AskName {
  val askNameAndPrintReply =
    for {
      _ <- printLn("Tell me your name!")
      n <- readLn()
      _ <- printLn(s"Hi! Your name seems to be ${n}")
    } yield ()
}

object PrintNameReceived {
  val getNamePrintAndReturnWelcomeHtml =
    for {
      name <- getName
      _ <- printLn(s"Name Received ${name}")
    } yield s"<html><body>Hi ${name}</body></html>"
}
