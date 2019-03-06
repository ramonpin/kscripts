@file:DependsOn("io.arrow-kt:arrow-core:0.7.1")
@file:DependsOn("io.arrow-kt:arrow-syntax:0.7.1")
@file:DependsOn("io.arrow-kt:arrow-typeclasses:0.7.1")
@file:DependsOn("io.arrow-kt:arrow-instances:0.6.1")
@file:DependsOn("io.arrow-kt:arrow-data:0.7.1")

import arrow.syntax.function.*

typealias Status  = Pair<Int, Int>
typealias Context = Int 

val f: (Context) -> ((Status) -> Status) = { c: Context , status: Status -> 
  print("f -> ")
  val (a, b) = status
  when {
    a < 0 && b < 0 -> Pair( a * c,  b * c)
    a < 0          -> Pair(-a * c,  b * c)
    b < 0          -> Pair( a * c, -b * c)
    else           -> Pair( a * c,  b * c) 
  }
}.curried()

val g: (Context) -> ((Status) -> Status) = { _: Context, status: Status -> 
  val (a, b) = status
  print("g -> ")
  Pair(a * b, a + b)
}.curried()

val context = 10

val h1 = listOf(f, g).map{ it(context)  }.reduce { comp, next -> comp andThen next }
println("\n andThen (f, g) ---------------------------------- ")
println(h1( 1 to  2))
println(h1(-1 to  2))
println(h1( 1 to -2))
println(h1(-1 to -2))

val h2 = listOf(f, g).map{ it(context) }.reduce { comp, next -> comp compose next }
println("\n compose (f, g) ---------------------------------- ")
println(h2( 1 to  2))
println(h2(-1 to  2))
println(h2( 1 to -2))
println(h2(-1 to -2))
