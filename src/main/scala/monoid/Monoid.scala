package monoid

trait Semigroup[T] {
  def combine(a: T, b: T): T
}

object Semigroup {
  def apply[T: Semigroup](): Semigroup[T] = implicitly[Semigroup[T]]
  implicit class SemigroupOps[A: Semigroup](a: A) {
    def |+|(b: A): A = Semigroup[A].combine(a, b)
  }
}

object SemigroupLaws {
  import Semigroup.SemigroupOps

  def associativity[A: Semigroup](a: A, b: A, c: A): Boolean =
    ((a |+| b) |+| c) == (a |+| (b |+| c))
}

trait Monoid[T] extends Semigroup[T] {
  def empty: T
}

object Monoid {
  def apply[T: Monoid]: Monoid[T] = implicitly[Monoid[T]]
}

object MonoidLaws {
  import Semigroup.SemigroupOps
  def identity[A: Monoid](a: A): Boolean = (a |+| Monoid[A].empty) == a
}
