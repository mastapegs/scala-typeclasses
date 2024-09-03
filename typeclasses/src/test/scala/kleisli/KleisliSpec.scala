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

    val doubleDiv24By: Double => Option[Double] = div24By >=> div24By

    val obtained_none = doubleDiv24By(0)
    val expected_none = None
    assertEquals(obtained_none, expected_none)

    val obtained_some = doubleDiv24By(2) // 24 / 2 == 12, 24 / 12 == 2
    val expected_some = Option(2.0)
    assertEquals(obtained_some, expected_some)
  }
}
