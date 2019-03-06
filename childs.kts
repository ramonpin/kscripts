abstract class Padre {

  init {
    println("Constructor Padre")
    println("Aquí this es $this")
    run()
    println("--------------------------------------------------")
  }
  
  fun run() = who(this) 
  abstract fun who(i: Padre);
} 

class Hijo: Padre() {
    init {
      println("--------------------------------------------------")
      println("Constructor Hijo")
      println("Aquí this es $this")
      println("--------------------------------------------------")
    }

    override fun who(i: Padre) {
      println("--------------------------------------------------")
      println("Método")
      println("El valor de this recibido es $i")
      println("--------------------------------------------------")
    }
}

val hijo = Hijo()
println("--------------------------------------------------")
println("Llamada directa")
hijo.run()

