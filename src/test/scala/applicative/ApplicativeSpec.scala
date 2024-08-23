package applicative

class ApplicativeSpec extends munit.FunSuite {
  import Applicative._

  case class TestClass[A](value: A)
  implicit val TestClassApplicative: Applicative[TestClass] =
    new Applicative[TestClass] {
      def pure[A](a: A): TestClass[A] = TestClass(a)
      def ap[A, B](ff: TestClass[A => B])(fa: TestClass[A]): TestClass[B] =
        TestClass(ff.value(fa.value))
    }

  test("can use pure") {
    val obtained = pure(3)
    val expected = TestClass(3)

    assertEquals(obtained, expected)
  }

  test("can use applicative apply") {
    val obtained = ap(TestClass((x: Int) => x + 2))(TestClass(3))
    val obtained2 = TestClass((x: Int) => x + 2) <*> TestClass(3)

    val expected = TestClass(5)

    assertEquals(obtained, expected)
    assertEquals(obtained2, expected)
  }

  test("Can mix and match pure and ap") {
    val obtained = pure((x: Int) => x + 3) <*> pure(4)
    val expected = pure(7)

    assertEquals(obtained, expected)
  }
}
