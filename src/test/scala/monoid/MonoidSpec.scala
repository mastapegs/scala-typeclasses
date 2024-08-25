package monoid

import munit.FunSuite

class MonoidSpec extends FunSuite {
  import monoid.Semigroup.SemigroupOps

  case class TestClass[A](value: A)

  implicit val IntProductMonoid: Monoid[Int] = new Monoid[Int] {
    def combine(a: Int, b: Int): Int = a * b
    def empty = 1
  }
  implicit def TestClassMonoid[A: Monoid]: Monoid[TestClass[A]] =
    new Monoid[TestClass[A]] {
      def empty: TestClass[A] = TestClass(Monoid[A].empty)
      def combine(a: TestClass[A], b: TestClass[A]): TestClass[A] = TestClass(
        a.value |+| b.value
      )
    }

  test("semigroups can combine") {
    val obtained = TestClass(3) |+| TestClass(4)
    val expected = TestClass(12)
    assertEquals(obtained, expected)
  }
}
