package monoid

import munit.FunSuite
import munit.ScalaCheckSuite
import org.scalacheck.Prop.forAll
import org.scalacheck.Arbitrary

class MonoidSpec extends ScalaCheckSuite {
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

  implicit def ArbitraryTestClass[A: Arbitrary]: Arbitrary[TestClass[A]] =
    Arbitrary(Arbitrary.arbitrary[A].map(TestClass(_)))

  test("semigroups can combine") {
    val obtained = TestClass(3) |+| TestClass(4)
    val expected = TestClass(12)
    assertEquals(obtained, expected)
  }

  test("monoids have an identity associated with their combine") {
    val obtained = TestClass(7) |+| Monoid[TestClass[Int]].empty
    val expected = TestClass(7)
    assertEquals(obtained, expected)
  }

  property("upholds semigroup associativity law") {
    forAll { (a: TestClass[Int], b: TestClass[Int], c: TestClass[Int]) =>
      val obtained = SemigroupLaws.associativity(a, b, c)
      val expected = true
      assertEquals(obtained, expected)
    }
  }

  property("upholds monoid identity law") {
    forAll { (a: TestClass[Int]) =>
      val obtained = MonoidLaws.identity(a)
      val expected = true
      assertEquals(obtained, expected)
    }
  }
}
