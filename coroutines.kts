@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")

import kotlinx.coroutines.*

launch(context=newSingleThreadContext("SushiThread")) {
  println("Hola")
}
