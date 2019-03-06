@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
@file:DependsOn("io.arrow-kt:arrow-effects:0.8.2")

import kotlinx.coroutines.*

GlobalScope.launch { // launch new coroutine in background and continue
    delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
    println("World!") // print after delay
}

println("Hello,") // main thread continues while coroutine is delayed
Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
