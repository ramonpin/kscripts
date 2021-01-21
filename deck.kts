val N = 1_000_000

val suits    = listOf("H", "S", "D", "T")
val ranks    = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
val deck     = suits.flatMap{ s -> ranks.map { r -> Pair(r, s) } }

fun hits()   = deck.zip(deck.shuffled()).any{ (c1, c2) -> c1 == c2 }

val total_hits = (1..N).sumBy{ if(hits()) 1 else 0 }
println(total_hits.toDouble() / N.toDouble())
