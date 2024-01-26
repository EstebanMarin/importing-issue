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
import natchez.noop.NoopTrace

object CircuitbreakerSandBoxSuite extends IOSuite {

  implicit val noopTrace: NoopTrace[IO] = NoopTrace[IO]

  val circuitBreakerFactory: CircuitBreakerFactory[IO] = impl(
    ???,
    ???,
    ???
  )

  val circuitBreaker: IO[Nothing] =
    circuitBreakerFactory
      .customCircuitBreaker("soapAction", "id")
      .flatMap((cb: CircuitBreaker[IO]) => ???)

  def modifiedCircuitBreaker[A](fa: IO[A]): IO[Nothing] =
    circuitBreakerFactory
      .customCircuitBreaker("soapAction", "id")
      .flatMap((cb: CircuitBreaker[IO]) =>
        cb.doOnClosed(IO(println("closed"))).protect(???)
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
