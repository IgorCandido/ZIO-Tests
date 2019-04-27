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
      userData <- UserDataProvision.userData
      _ <- printLn(s"Name Received ${userData.name} your authenticated with ${userData.userName}")
    } yield s"<html><body>Hi ${userData.name} authenticate ${userData.userName}</body></html>"
}
