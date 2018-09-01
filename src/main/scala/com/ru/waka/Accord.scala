package com.ru.waka

import com.wix.accord.{NullSafeValidator, RuleViolation, Validator, validate}
import com.wix.accord.ViolationBuilder.singleViolationToFailure
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
    p.bs is myNotEmpty as "その値"
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

    println(validate(Piyo(Nil, Nil)))
    // Failure(Set(as must not be empty, その値 List() は空じゃだめなんじゃ〜))
    println(validate(Piyo(Seq("a"), Seq(1))))
    // Success
  }

  case class Hoge(a: String, b: Int)

  case class Fuga(as: Seq[Int])

  case class Piyo(as: Seq[String], bs: Seq[Int])

  def myNotEmpty[T <: Seq[_]]: Validator[ T ] =
    new NullSafeValidator[ T ](
      test    = x => x.nonEmpty,
      failure = x => RuleViolation(x, s"$x は空じゃだめなんじゃ〜")
    )
}