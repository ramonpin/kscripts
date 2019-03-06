typealias Data           = Pair<Int, String>
typealias Processor      = (String) -> Any
typealias MultiProcessor = (String) -> List<Data>
typealias MonoProcessor  = (String) -> Data

val multiprocessor: MultiProcessor = { str -> listOf(Pair(0, str), Pair(1, str)) }
val monoprocessor: MonoProcessor   = { str -> Pair(0, str) }

fun convert(pp: Processor): MultiProcessor = { str ->
  val res = pp(str)
  when(res) {
    is List<*> -> res
    else       -> list(res)
  }
}

fun process(data: List<String>, processor: Processor): List<Data> =
  data.flatMap(convert(processor))

val data: List<String> = listOf("hola", "adios", "perico")
println(process(data, multiprocessor))
println(process(data, convert(monoprocessor)))
