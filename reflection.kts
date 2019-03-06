@file:DependsOn("org.reflections:reflections:0.9.9")
import org.reflections.*

open class Pepe {
  open var properties = mapOf<String, Any>() 
}

class Luis: Pepe() {
  override var properties = mapOf<String, Any>("1" to 2, "3" to "4")
}

class Juan: Pepe() {
  override var properties = mapOf<String, Any>("3" to 5, "2" to "2")
}

val tasks = Reflections().getSubTypesOf(Pepe::class.java)
val checks = tasks
  .map{ it.newInstance() as Pepe }
  .map{ it.properties }
  .zip(tasks)
  .filter { it.first.contains("3") }
  .map { Pair(it.first["3"] is String, it.second) }
  .filter { it.first == false }
  .map { it.second }

checks.forEach(::println)

