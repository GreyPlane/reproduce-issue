package controllers

import com.google.inject.Inject
import org.apache.pekko.stream.Materializer
import org.apache.pekko.stream.scaladsl.Flow
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.capabilities.pekko.PekkoStreams
import sttp.tapir._
import sttp.tapir.server.play.PlayServerInterpreter

import scala.concurrent.Future

class WebsocketRouter @Inject() (implicit mat: Materializer)
    extends SimpleRouter {

  val echo = endpoint
    .in("ws" / "echo")
    .out(
      webSocketBody[
        String,
        CodecFormat.TextPlain,
        String,
        CodecFormat.TextPlain
      ](PekkoStreams)
    )
    .serverLogicPure[Future](_ => Right(Flow[String].map(identity)))

  def routes: Routes = PlayServerInterpreter().toRoutes(List(echo))
}
