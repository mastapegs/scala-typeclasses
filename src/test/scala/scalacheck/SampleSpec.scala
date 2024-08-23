package scalacheck

import munit.ScalaCheckSuite
import org.scalacheck.Prop._

class SampleSpec extends ScalaCheckSuite {
  property("Sample Scalacheck Test") {
    forAll { (a: String, b: String) =>
      assertEquals((a + b).size, a.size + b.size)
    }
  }
}
