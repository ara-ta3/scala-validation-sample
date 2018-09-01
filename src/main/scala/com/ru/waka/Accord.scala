package com.ru.waka

import com.wix.accord.{validate, Validator}
import com.wix.accord.dsl._

object Accord {
  implicit val hogeValidator: Validator[Hoge] = validator[Hoge] { h =>
    h.a is notEmpty
    h.b is in(1, 2)
  }

  implicit val FugaValidator: Validator[Fuga] = validator { f =>
    f.as has size > 0
  }

  implicit val PiyoValidator: Validator[Piyo] = validator { p =>
    p.as is notEmpty
  }

  /**
    * @see http://wix.github.io/accord/dsl.html#combinators
    */
  def main(args: Array[String]): Unit = {
    println(validate(Hoge(a = "", b = 10)))
    // Failure(Set(a must not be empty, b with value "10" got 10, expected one of: [1, 2]))

    println(validate(Fuga(Nil)))
    // Failure(Set(as has size 0, expected more than 0))
    println(validate(Fuga(Seq(1, 2, 3))))
    // Success

    println(validate(Piyo(Nil)))
    // Failure(Set(as must not be empty))
    println(validate(Piyo(Seq("a"))))
    // Success
  }

  case class Hoge(a: String, b: Int)

  case class Fuga(as: Seq[Int])

  case class Piyo(as: Seq[String])
}