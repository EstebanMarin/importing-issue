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

import weaver.IOSuite
import cats.effect.{IO, IOApp}
import io.chrisdavenport.circuit.CircuitBreaker
import cats.implicits._
import io.chrisdavenport.circuit._
import io.chrisdavenport.rediculous._
import io.chrisdavenport.rediculous.concurrent.RedisCircuit
import natchez.Trace
import org.typelevel.log4cats.StructuredLogger
import cats.effect.kernel._
import scala.concurrent.duration.FiniteDuration
import shapeless.PolyDefns
import CircuitbreakerSandBox._
import CircuitbreakerSandBox.CircuitBreakerFactory

object CircuitbreakerSandBoxSuite extends IOSuite {

  implicit val noopTrace: Trace[IO] = ???

  val circuitBreakerFactory: CircuitBreakerFactory[IO] = impl(
    ???,
    ???,
    ???
  )

  type Res = String

  def sharedResource: Resource[IO, String] = Resource
    .make(
      IO(println("Making resource"))
        .as("123")
    )(n => IO(println(s"Closing resource $n")))

  test("test, but resource not visible") {
    IO(expect(123 == 123))
  }

  test("test with resource") { n =>
    IO(expect(n == "123"))
  }

  test("test with resource and a logger") { (n, log) =>
    log.info("log was available") *>
      IO(expect(n == 123))
  }
}
