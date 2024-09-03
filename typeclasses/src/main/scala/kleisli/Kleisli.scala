package kleisli

import monad.Monad
import Monad._

case class Kleisli[F[_]: Monad, A, B](f: A => F[B]) {
  def compose[C](kg: Kleisli[F, B, C]): Kleisli[F, A, C] =
    Kleisli(f(_).flatMap(kg.f(_)))
}

object Kleisli {
  implicit class KleisliOps[F[_]: Monad, A, B](f: A => F[B]) {
    def >=>[C](g: B => F[C]): A => F[C] = (Kleisli(f) compose Kleisli(g)).f
  }
}
