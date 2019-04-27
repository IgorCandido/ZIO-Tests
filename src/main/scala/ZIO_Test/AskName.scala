package ZIO_Test

import ZIO_Test.ConsoleDSL._

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
      name <- UserData.name
      userName <- UserData.userName
      _ <- printLn(s"Name Received ${name} your authenticated with ${userName}")
    } yield s"<html><body>Hi ${name} authenticate ${userName}</body></html>"
}
