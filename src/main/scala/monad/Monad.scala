package monad

import applicative.Applicative

trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit class MonadOps[F[_]: Monad, A](fa: F[A]) {
    def flatMap[B](f: A => F[B]): F[B] = implicitly[Monad[F]].flatMap(fa)(f)
  }
}
