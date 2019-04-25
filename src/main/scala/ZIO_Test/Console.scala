package ZIO_Test
import scalaz.zio.ZIO
import scala.{Console => SConsole}

trait Console {
  def console: Console.Service
}

object Console {
  trait Service {
    def printLn(line: String): ZIO[Console, Nothing, Any]
    def readLn(): ZIO[Console, Nothing, String]
  }
}

trait LiveConsole extends Console {
  override def console: Console.Service = new Console.Service {
    override def printLn(line: String): ZIO[Console, Nothing, Any] =
      ZIO.effectTotal(println(line))
    override def readLn(): ZIO[Console, Nothing, String] =
      ZIO.effectTotal(SConsole.in.readLine())
  }
}

object LiveConsole extends LiveConsole

object ConsoleDSL extends Console.Service{
  override def printLn(
    line: String
  ): ZIO[Console, Nothing, Any] = ZIO.accessM(_.console printLn line)
  override def readLn(): ZIO[Console, Nothing, String] = ZIO.accessM(_.console readLn())
}
