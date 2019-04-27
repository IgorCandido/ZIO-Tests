package ZIO_Test

import scalaz.zio.ZIO

trait ReaderFromRequest {
  def requestReader: ReaderFromRequest.Service
}

object ReaderFromRequest{
  trait Service{
    def name: ZIO[ReaderFromRequest, Any, String]
    def userName: ZIO[ReaderFromRequest, Any, String]
  }
}

class AkkaHTTPReaderFromRequest(_userName: String, _name: String) extends ReaderFromRequest{
  override val requestReader: ReaderFromRequest.Service = new ReaderFromRequest.Service {
    override def name: ZIO[ReaderFromRequest, Any, String] = ZIO.effectTotal{ _name }
    override def userName: ZIO[ReaderFromRequest, Any, String] = ZIO.effectTotal{ _userName }
  }
}

object UserData {
  def name: ZIO[ReaderFromRequest, Any, String] = ZIO.accessM(_.requestReader name)
  def userName: ZIO[ReaderFromRequest, Any, String] = ZIO.accessM(_.requestReader userName)
}
