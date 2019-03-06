sealed class Router {

  fun route(vararg xs: Any): Any = when(this) {
    is Get -> controller(xs[0] as Int, xs[1] as Int)
    is GetAll -> controller(xs[0] as Int)
  }

  data class Get(val controller: (Int, Int) -> Long) : Router()
  data class GetAll(val controller: (Int) -> Long) : Router()
}

val routes = mapOf(
  "get" to Router.Get { i, j -> (i + j).toLong() },
  "get_all" to Router.GetAll { i -> (i * 2).toLong() }
)

println(routes["get"])

val routerFunction = routes["get"]
if(routerFunction != null) {
  println(routerFunction.route(1, 2))
}
