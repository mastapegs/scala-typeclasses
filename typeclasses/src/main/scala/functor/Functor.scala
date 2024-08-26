package functor

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object FunctorLaws {
  import Functor._

  def identity[F[_]: Functor, A](fa: F[A]): Boolean = fa.map(a => a) == fa
  def composition[F[_]: Functor, A, B, C](fa: F[A])(f: A => B)(
      g: B => C
  ): Boolean = fa.map(f).map(g) == fa.map(f andThen g)
}

object Functor {
  implicit class FunctorOps[F[_]: Functor, A](fa: F[A]) {
    def map[B](f: A => B): F[B] = implicitly[Functor[F]].map(fa)(f)
    def `<$>`[B](f: A => B): F[B] = map(f)
  }
}
