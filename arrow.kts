@file:DependsOn("io.arrow-kt:arrow-core:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-syntax:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-typeclasses:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-instances-core:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-instances-data:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-effects:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-effects-instances:0.8.2")
@file:DependsOn("io.arrow-kt:arrow-data:0.8.2")

import arrow.core.None
import arrow.core.Option
import arrow.core.Predicate
import arrow.data.ListK
import arrow.data.ListKOf
import arrow.data.combineK
import arrow.data.k
import arrow.syntax.function.compose
import arrow.syntax.function.curried
import arrow.syntax.function.partially1
import arrow.syntax.function.pipe

// Test Data
val lst = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

// First pseudo functional version
val fGreaterThanThreeAndEven: (List<Int>) -> List<Int> =
  { it.filter { it > 3 && it % 2 == 0 } }

println(fGreaterThanThreeAndEven(lst))

// Better functional version
infix fun <T>Predicate<T>.and(other: Predicate<T>): Predicate<T> =
  { it -> this(it) && other(it) }

val sGreaterThanThree: Predicate<Int> = { it > 3 }
val sEven: Predicate<Int> = { it % 2 == 0 }
val sGreaterThanThreeAndEven: Predicate<Int> = sGreaterThanThree and sEven
println(lst.filter(sGreaterThanThreeAndEven))

// Another functional version
val filter: ((Predicate<Int>, List<Int>) -> List<Int>) =
  { f: Predicate<Int>, lst: List<Int> -> lst.filter(f) }

val tTakeEvens = filter.partially1 { it % 2 == 0 }
val tTakeGreateThanThree  = filter.partially1 { it > 3 }
val tTakeEvensAndGreaterThanThree = tTakeGreateThanThree compose tTakeEvens

println(tTakeEvensAndGreaterThanThree(lst))

// Piping
val ufilter: (Predicate<Int>) -> (List<Int>) -> List<Int> = filter.curried()
val pTakeEvens: (List<Int>) -> List<Int> = ufilter { it % 2 == 0 }
val pTakeGreateThanThree: (List<Int>) -> List<Int>  = ufilter { it > 3 }
val pTakeEvensGreaterThanThree: (List<Int>) -> List<Int> =
  { lst: List<Int> -> lst pipe pTakeEvens pipe pTakeGreateThanThree }

println(pTakeEvensGreaterThanThree(lst))

// Option
fun takesAString(s: String) = "==${s}=="
val x: Option<String> = Option.empty()
val y: Option<String> = Option.just("hola")
println(x.map(::takesAString))
println(y.map(::takesAString))
println(y.map(String::toUpperCase)
         .map(::takesAString))

// Monoid
// val lst2 = listOf("a", "b", "c").map { Option.just(it) }.k()
// println(lst2.map(::takesAString compose String::toUpperCase))
// println(lst2.foldRight(""){ a, b -> "$a$b" })

val lst3 = listOf("a", "b", "c")
println(lst3.map(::takesAString compose String::toUpperCase))
println(lst3.foldRight(""){ a, b -> "$a$b" })

// Infinite sequence
val fibonacci = generateSequence(0 to 1) { it.second to it.first + it.second }.map { it.first }.k()
println(fibonacci.take(10).toList())
println(fibonacci.map { it * 2 }.takeWhile { it < 10 }.toList())
