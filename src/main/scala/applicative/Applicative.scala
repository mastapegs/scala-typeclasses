package applicative

import functor.Functor

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}

object Applicative {
  def ap[F[_]: Applicative, A, B](ff: F[A => B])(fa: F[A]): F[B] =
    implicitly[Applicative[F]].ap(ff)(fa)

  def pure[F[_]: Applicative, A](a: A) =
    implicitly[Applicative[F]].pure(a)

  implicit class ApplicativeOpsAp[F[_]: Applicative, A, B](ff: F[A => B]) {
    def <*>(fa: F[A]): F[B] = implicitly[Applicative[F]].ap(ff)(fa)
  }
}
