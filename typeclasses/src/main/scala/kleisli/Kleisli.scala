package kleisli

import monad.Monad
import Monad._

case class Kleisli[F[_]: Monad, A, B](f: A => F[B]) {
  def compose[C](g: B => F[C]): A => F[C] = a => f(a).flatMap(g)
}

object Kleisli {
  implicit class KleisliOps[F[_]: Monad, A, B](kf: Kleisli[F, A, B]) {
    def >=>[C](kg: Kleisli[F, B, C]): Kleisli[F, A, C] = Kleisli(
      kf.compose(kg.f)
    )
  }
}
