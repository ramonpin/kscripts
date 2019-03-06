@file:DependsOn("io.arrow-kt:arrow-core:0.8.1")
@file:DependsOn("io.arrow-kt:arrow-syntax:0.8.1")

import arrow.syntax.function.pipe
import arrow.syntax.function.forwardCompose
import arrow.syntax.function.andThen

val lst = (1..10).toList()

// Another functional version
fun <T>filter(f: (T) -> Boolean) = { lst: List<T> -> lst.filter(f) }
fun <T,R>map(f: (T) -> R) = { lst: List<T> -> lst.map(f) }

// Piping
val pDouble: (List<Int>) -> List<Double> = map { it * 2.0 }
val pTakeEvens: (List<Int>) -> List<Int> = filter { it % 2 == 0 }
val pTakeGreaterThanThree: (List<Int>) -> List<Int>  = filter { it > 3 }
val pTakeEvensGreaterThanThree: (List<Int>) -> List<Double> = { lst: List<Int> ->
  lst                   pipe
  pTakeEvens            pipe
  pTakeGreaterThanThree pipe
  pDouble
}

val atTakeEvensGreaterThanThree: (List<Int>) -> List<Double> =
  pTakeEvens            andThen
  pTakeGreaterThanThree andThen
  pDouble

val fcTakeEvensGreaterThanThree: (List<Int>) -> List<Double> =
    pTakeEvens            forwardCompose
    pTakeGreaterThanThree forwardCompose
    pDouble

println(pTakeEvensGreaterThanThree(lst))
println(atTakeEvensGreaterThanThree(lst))
println(fcTakeEvensGreaterThanThree(lst))
