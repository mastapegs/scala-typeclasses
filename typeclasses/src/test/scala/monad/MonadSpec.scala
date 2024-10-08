package monad

class MonadSpec extends munit.FunSuite {
  import Monad._

  case class TestClass[A](value: A)
  implicit val MonadTestClass: Monad[TestClass] = new Monad[TestClass] {
    def pure[A](a: A): TestClass[A] = TestClass(a)
    def ap[A, B](ff: TestClass[A => B])(fa: TestClass[A]): TestClass[B] =
      TestClass(ff.value(fa.value))
    def flatMap[A, B](fa: TestClass[A])(f: A => TestClass[B]): TestClass[B] = f(
      fa.value
    )
  }

  test("can flatmap") {
    val obtained = TestClass(5).flatMap((x: Int) => TestClass(x + 4))
    val expected = TestClass(9)
    assertEquals(obtained, expected)
  }

  test("can use bind/chain/flatMap shorthand (>>=)") {
    val obtained = TestClass(7) `>>=` (x => TestClass(x + 2))
    val exptected = TestClass(9)
    assertEquals(obtained, exptected)
  }
}
