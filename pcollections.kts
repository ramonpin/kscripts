@file:DependsOn("org.pcollections:pcollections:2.1.3")

import org.pcollections.HashTreePSet
import org.pcollections.MapPSet

val set: MapPSet<String> = HashTreePSet.empty<String>().apply { plus("something") }
println(set)
println(set.plus("something else"))
println(set)
