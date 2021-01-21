@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.0")

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.*

fun main() {
  CoroutineScope().launch() {
    delay(1000)
    println("Hello from Kotlin Coroutines!")
  }
}
