@file:DependsOn("org.funktionale:funktionale-all:1.2")

import org.funktionale.either.Either
import org.funktionale.either.Either.*
import org.funktionale.partials.*

fun f(i: Int): Either<String, Int> = if(i % 2 == 0) {
  Left("Debe ser impar")
} else {
  Right(i * 2)
}

fun main() {
  val errores = mutableListOf<String>("Error gordo")
  var b = 0

  val a = f(2)
  when(a) {
    is Left -> errores += a.left().get()
    is Right -> b += a.right().get()
  }

  println(errores)
  println(b)
}

main()
