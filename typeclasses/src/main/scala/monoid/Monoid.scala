package monoid

trait Semigroup[T] {
  def combine(a: T, b: T): T
}

object Semigroup {
  def apply[T: Semigroup](): Semigroup[T] = implicitly[Semigroup[T]]
  implicit class SemigroupOps[A: Semigroup](a: A) {
    def |+|(b: A): A = Semigroup[A].combine(a, b)
  }
  implicit class SemigroupOpsReduce[A: Semigroup](list: List[A]) {
    def reduceAll(): A = Semigroup.reduceAll(list)
  }
  def reduceAll[A: Semigroup](list: List[A]): A = list.reduce(_ |+| _)
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
  import Semigroup.SemigroupOps

  def apply[T: Monoid]: Monoid[T] = implicitly[Monoid[T]]
  implicit class MonoidOps[A: Monoid](list: List[A]) {
    def combineAll(): A = list.foldLeft(Monoid[A].empty)(_ |+| _)
  }
  def combineAll[A: Monoid](list: List[A]): A =
    list.foldLeft(Monoid[A].empty)(_ |+| _)
}

object MonoidLaws {
  import Semigroup.SemigroupOps
  def identity[A: Monoid](a: A): Boolean = (a |+| Monoid[A].empty) == a
}
