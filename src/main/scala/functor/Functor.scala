package functor

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit class FunctorOps[F[_], A](fa: F[A])(implicit functor: Functor[F]) {
    def map[B](f: A => B): F[B] = functor.map(fa)(f)
  }
}
