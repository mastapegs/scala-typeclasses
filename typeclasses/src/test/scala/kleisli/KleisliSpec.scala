package kleisli

import munit.ScalaCheckSuite
import kleisli.Kleisli
import Kleisli._
import monad.Monad
import Monad._

class KleisliSpec extends ScalaCheckSuite {
  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Option(a)
    def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] =
      fa.flatMap((a: A) => ff.map(_(a)))
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.flatMap(f)
  }

  test("option kleisli arrows compose") {
    val div24By: Double => Option[Double] =
      (x: Double) => if (x == 0) None else Some(24 / x)

    val kDiv24By =
      Kleisli[Option, Double, Double](div24By)
    val kDoubleDiv24By: Kleisli[Option, Double, Double] = kDiv24By >=> kDiv24By

    val obtained_none = kDoubleDiv24By.f(0)
    val expected_none = None
    assertEquals(obtained_none, expected_none)

    val obtained_some = kDoubleDiv24By.f(2) // 24 / 2 == 12. 24 / 12 == 2
    val expected_some = Option(2.0)
    assertEquals(obtained_some, expected_some)
  }
}
