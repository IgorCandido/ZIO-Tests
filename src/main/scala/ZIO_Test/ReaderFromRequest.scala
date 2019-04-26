package ZIO_Test

import scalaz.zio.ZIO

trait ReaderFromRequest {
  val requestReader: ReaderFromRequest.Service
}

object ReaderFromRequest{
  trait Service{
    def getName: ZIO[ReaderFromRequest, Any, String]
  }
}

class AkkaHTTPReaderFromRequest(name: String) extends ReaderFromRequest{
  override val requestReader: ReaderFromRequest.Service = new ReaderFromRequest.Service {
    override def getName: ZIO[ReaderFromRequest, Any, String] = ZIO.effectTotal{ name }
  }
}

object ReaderFromRequestDSL extends ReaderFromRequest.Service{
  override def getName: ZIO[ReaderFromRequest, Any, String] = ZIO.accessM(_.requestReader getName)
}
