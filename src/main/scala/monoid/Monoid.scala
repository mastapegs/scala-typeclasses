package monoid

trait Semigroup[T] {
  def combine(a: T, b: T): T
}

object Semigroup {
  def apply[T: Semigroup](): Semigroup[T] = implicitly[Semigroup[T]]
  implicit class SemigroupOps[S: Semigroup](s: S) {
    def |+|(t: S): S = Semigroup[S].combine(s, t)
  }
}

trait Monoid[T] extends Semigroup[T] {
  def empty: T
}

object Monoid {
  def apply[T: Monoid]: Monoid[T] = implicitly[Monoid[T]]
}
