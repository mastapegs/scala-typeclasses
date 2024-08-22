package applicative

import functor.Functor

trait Applicative[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}

object Applicative {
  def ap[F[_], A, B](ff: F[A => B])(fa: F[A])(implicit
      applicative: Applicative[F]
  ): F[B] = applicative.ap(ff)(fa)

  def pure[F[_], A](a: A)(implicit applicative: Applicative[F]) =
    applicative.pure(a)

  implicit class ApplicativeOpsAp[F[_], A, B](ff: F[A => B])(implicit
      applicative: Applicative[F]
  ) {
    def <*>(fa: F[A]): F[B] = applicative.ap(ff)(fa)
  }
}
