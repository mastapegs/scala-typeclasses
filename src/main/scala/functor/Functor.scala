package functor

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit class FunctorOps[F[_]: Functor, A](fa: F[A]) {
    def map[B](f: A => B): F[B] = implicitly[Functor[F]].map(fa)(f)
    def `<$>`[B](f: A => B): F[B] = map(f)
  }
}
