package main

import monoid._
import monoid.Monoid._

object StringImplicits {
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    def combine(a: String, b: String): String = a + b
    def empty: String = ""
  }
}

object Main extends App {
  import StringImplicits._

  println("example-app Main")
  println(List("Test", "1", "2", "3").combineAll)
}
