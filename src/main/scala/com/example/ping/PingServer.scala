package com.example.ping

import org.apache.pekko
import pekko.actor.ActorSystem
import pekko.http.scaladsl.Http
import pekko.http.scaladsl.server.Directives
import pekko.http.scaladsl.unmarshalling.Unmarshal
import org.slf4j.LoggerFactory

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.DurationInt

object PingServer extends App with Directives {
  val logger = LoggerFactory.getLogger(PingServer.getClass)
  implicit val system: ActorSystem = ActorSystem("pingserver")
  val timeout = 10.seconds
  sys.addShutdownHook(system.terminate())

  implicit val executionContext: ExecutionContext = system.dispatcher

  val routes = extractRequest { request =>
    logger.info(s"uri: ${request.uri}")
    logger.info(s"method: ${request.method.value}")
    logger.info(s"headers:\n${request.headers.map(_.toString).mkString("\n")}")
    Unmarshal(request.entity).to[String].map { requestText =>
      logger.info(s"message:\n$requestText")
    }
    complete("")
  }

  val future = Http().newServerAt("0.0.0.0", 9999).bind(routes)
  Await.result(future, timeout)
  Thread.sleep(Long.MaxValue)
}
