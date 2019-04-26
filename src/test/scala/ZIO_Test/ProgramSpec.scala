package ZIO_Test

import org.scalatest.FeatureSpec
import scalaz.zio.{DefaultRuntime, ZIO}

class TestConsole(name: String) extends Console{
  var writeStack: Seq[String] = Seq.empty

  override def console: Console.Service = new Console.Service {

    override def printLn(line: String): ZIO[Console, Nothing, Any] = ZIO.effectTotal{ writeStack = writeStack :+ line }

    override def readLn(): ZIO[Console, Throwable, String] = ZIO.effect{ name }
  }
}

class ProgramSpec extends FeatureSpec{
  Feature("ReadName"){
    Scenario("Read name"){
      val runtime = new DefaultRuntime {}
      val testConsole = new TestConsole("Test")
      runtime.unsafeRun(AskName.askNameAndPrintReply.provide(testConsole))

      assert(testConsole.writeStack(0) == "Tell me your name!")
      assert(testConsole.writeStack(1) == "Hi! Your name seems to be Test")
    }
  }
}
