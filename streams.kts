val iterable: Iterable<String> = listOf("Hello", "World")
val sequence: Sequence<String> = iterable.asSequence()
sequence.forEach(::println)

