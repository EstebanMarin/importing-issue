//> using scala 2.13.12
//> using toolkit typelevel:default
//> using dependency io.chrisdavenport::circuit:0.5.1
//> using dependency io.chrisdavenport::rediculous:0.5.1
//> using dependency io.chrisdavenport::rediculous-concurrent:0.4.0
//> using dependency org.tpolecat::natchez-core:0.3.5
//> using dependency org.tpolecat::natchez-noop:0.3.5
//> using dependency io.chrisdavenport::natchez-rediculous:0.1.0
//> using dependency org.typelevel::log4cats-core:2.6.0
//> using dependency org.typelevel::log4cats-noop:2.6.0
//> using test.dep com.disneystreaming::weaver-cats:0.8.3
//> using testFramework "weaver.framework.CatsEffect"
//> using test.toolkit typelevel:default

import cats.effect.{IO, IOApp}
import io.chrisdavenport.circuit.CircuitBreaker
import cats.implicits._
import io.chrisdavenport.circuit.Backoff
import io.chrisdavenport.circuit.CircuitBreaker
import io.chrisdavenport.rediculous.RedisCommands
import io.chrisdavenport.rediculous.RedisConnection
import io.chrisdavenport.rediculous.concurrent.RedisCircuit
import natchez.Trace
import org.typelevel.log4cats.StructuredLogger
import cats.effect.kernel._
import scala.concurrent.duration.FiniteDuration
import shapeless.PolyDefns

object CircuitbreakerSandBox extends IOApp.Simple {
  final case class CircuitConfig(
      acquireTimeout: FiniteDuration,
      lockDuration: FiniteDuration,
      setDuration: FiniteDuration,
      maxFailures: Int,
      resetTimeout: FiniteDuration,
      maxResetTimeout: FiniteDuration
  )
  trait CircuitBreakerFactory[F[_]] {
    def customCircuitBreaker(
        action: String,
        id: String
    ): F[CircuitBreaker[F]]
  }

  object CircuitBreakerFactory {
    def circuitOp[T[_]: Async, A](
        circuiting: => CircuitBreaker[T],
        bypassOpsCircuit: Boolean
    )(fa: T[A]): T[A] = ???

  }

  def impl[F[_]: Async: Trace](
      conn: RedisConnection[F],
      circuitConfig: CircuitConfig,
      logger: StructuredLogger[F]
  ): CircuitBreakerFactory[F] = {
    new CircuitBreakerFactory[F] {
      def customCircuitBreaker(
          soapAction: String,
          i: String
      ): F[CircuitBreaker[F]] = ???
    }

  }

  def run: IO[Unit] =
    IO(println("should run with the redis instance"))
}
