sealed class Option<out T> {

  object None: Option<Nothing>() {
    override fun toString() = "None"
  }

  data class Some<T>(val content: T): Option<T>() 
}

fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> = when (this) {
  Option.None -> Option.None
  is Option.Some -> Option.Some(transform(content))
}

fun <T, R> Option<T>.flatmap(transform: (T) -> Option<R>): Option<R> = when(this) {
  Option.None    -> Option.None
  is Option.Some -> transform(content)
}

fun <T> Option<T>.orElse(defaut: T): T = when(this) {
  Option.None    ->  defaut
  is Option.Some -> content
}

// Sample domain object...
data class Client(val id: Int, val name: String, val age: Int, val cash: Int)

// Simulates a database access that gives a client if it exists...
fun clientFromDB(id: Int): Option<Client> =
  if(id % 2 == 0) Option.None 
  else Option.Some(Client(id, "NAME_", id * 2, id * 10000))

fun formatClient(c: Client) = 
  "${c.name} has ${c.cash / 100}€"

fun useCase(id: Int): Option<String> = clientFromDB(id)
  .map { it.copy(age = it.age + 1) } // increment age
  .map { it.copy(name = "NONPUBLIC_${it.age}") } // Remove real name
  .map { it.copy(id = it.cash - 300 ) } // substract commision from account
  .map(::formatClient) // format result 

val msg = with(useCase(1)) { when(this) { 
    Option.None    -> "unknown"
    is Option.Some -> content 
  }
}

println(msg)

fun logicOne(n: Int): Option<Int> = if(n % 5 == 0) Option.None else Option.Some(n * 5)
fun logicTwo(n: Int): Option<Int> = if(n % 3 == 0) Option.None else Option.Some(n * 3)
fun complex(id: Int): Int = clientFromDB(id)
  .map{ it.id } // get client id if exists (normal func)
  .flatmap(::logicOne) // apply optional returning logic
  .flatmap(::logicTwo) // and another optional logic
  .orElse(0) // extract result or default

println(complex(1))
println(complex(3))
println(complex(5))
println(complex(15))

