import java.util.concurrent.locks.*
import java.util.*

// Return multiple values
data class DivisionResult(val quotient: Int, val remainder: Int)

fun divide(dividend: Int, divisor: Int): DivisionResult {
  val quotient = dividend.div(divisor)
  val remainder = dividend.rem(divisor)
  return DivisionResult(quotient, remainder)
}

val (a, b) = divide(5, 6)
println("a = $a   b = $b")

// Inlining lambda parameters
inline fun performHavingLock(lock: Lock, task: () -> Unit) {
  lock.lock()
  try {
    task()
  } finally {
    lock.unlock()
  }
}

// inline var
class InlineVarSample() {
  inline var a: String 
    get()  { return "hola" }
    set(_) { return }  
}

// Main script
performHavingLock(ReentrantLock()) {
  println("Wait for it!")
}

val ivs = InlineVarSample()
println(ivs.a)

// also and let
println("Also block")
val l = listOf(1,2,3)
  .also(::println)
  .let{ it.sortedDescending() }
  .also(::println)

// run block
val calendar = Calendar.Builder().run {
  build()
}
println(calendar)
