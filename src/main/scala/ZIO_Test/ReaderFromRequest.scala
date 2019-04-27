package ZIO_Test

import scalaz.zio.ZIO

case class UserData(name: String, userName: String)

trait ReaderFromRequest {
  def requestReader: ReaderFromRequest.Service
}

object ReaderFromRequest{
  trait Service{
    def userData: ZIO[ReaderFromRequest, Any, UserData]
  }
}

class AkkaHTTPReaderFromRequest(_userName: String, _name: String) extends ReaderFromRequest{
  val _userData = UserData(name= _name, userName = _userName)
  override val requestReader: ReaderFromRequest.Service = new ReaderFromRequest.Service {
    override def userData: ZIO[ReaderFromRequest, Any, UserData] = ZIO.effectTotal(_userData)
  }
}

object UserDataProvision {
  def userData: ZIO[ReaderFromRequest, Any, UserData] = ZIO.accessM(_.requestReader userData)
}
