@file:DependsOn("io.arrow-kt:arrow-core:0.6.1")
@file:DependsOn("io.arrow-kt:arrow-syntax:0.6.1")
@file:DependsOn("io.arrow-kt:arrow-typeclasses:0.6.1")
@file:DependsOn("io.arrow-kt:arrow-instances:0.6.1")
@file:DependsOn("io.arrow-kt:arrow-data:0.6.1")

import arrow.core.Either
import arrow.core.flatMap

fun f(i: Int) = if(i % 2 == 0) Either.right(i * 2) else Either.left("ERROR...")
fun g(i: Int) = i * 3

val le = Either.right(2)
val res = le.flatMap(::f).map(::g)
println(res)
