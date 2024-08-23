package functor

class FunctorSpec extends munit.FunSuite {
  import Functor._

  case class TestClass[A](value: A)
  implicit val TestClassFunctor: Functor[TestClass] = new Functor[TestClass] {
    def map[A, B](fa: TestClass[A])(f: A => B): TestClass[B] = fa match {
      case TestClass(value) => TestClass(f(value))
    }
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
}
