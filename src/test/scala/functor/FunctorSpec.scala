package functor

class FunctorSpec extends munit.FunSuite {
  import Functor._

  case class TestClass[A](value: A)
  implicit val TestClassFunctor: Functor[TestClass] = new Functor[TestClass] {
    def map[A, B](fa: TestClass[A])(f: A => B): TestClass[B] = TestClass(
      f(fa.value)
    )
  }

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

  test("upholds composition law") {
    val obtained = FunctorLaws.composition(TestClass(3))(_ * 2)(_ + 3)
    val expected = true
    assertEquals(obtained, expected)
  }
}
