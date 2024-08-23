package functor

import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll
import munit.ScalaCheckSuite

class FunctorSpec extends ScalaCheckSuite {
  import Functor._

  case class TestClass[A](value: A)
  implicit val TestClassFunctor: Functor[TestClass] = new Functor[TestClass] {
    def map[A, B](fa: TestClass[A])(f: A => B): TestClass[B] = TestClass(
      f(fa.value)
    )
  }

  // Arbitrary TestClass
  implicit def arbitraryTestClass[A: Arbitrary]: Arbitrary[TestClass[A]] =
    Arbitrary(Arbitrary.arbitrary[A].map(TestClass(_)))

  // Arbitrary Functions for composition law
  implicit def arbitraryFunctions[A: Arbitrary, B: Arbitrary]
      : Arbitrary[A => B] = Arbitrary(
    Arbitrary.arbitrary[B].map(b => (_: A) => b)
  )

  test("functors can map") {
    val obtained = TestClass(3).map(_ * 2)
    val expected = TestClass(6)
    assertEquals(obtained, expected)
  }

  test("use shorthand map infix") {
    val obtained = TestClass(4) `<$>` (_ + 2)
    val expected = TestClass(6)
    assertEquals(obtained, expected)
  }

  test("upholds functor identity law") {
    val obtained = FunctorLaws.identity(TestClass(17))
    val expected = true
    assertEquals(obtained, expected)
  }

  property("upholds functor composition law") {
    forAll { (tc: TestClass[Int], f: Int => String, g: String => Double) =>
      val obtained = FunctorLaws.composition(tc)(f)(g)
      val expected = true
      assertEquals(obtained, expected)
    }
  }
}
