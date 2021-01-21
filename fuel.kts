@file:DependsOn("com.github.kittinunf.fuel:fuel:2.0.1")

import com.github.kittinunf.fuel.Fuel
import java.nio.charset.Charset

System.setProperty("http.proxyHost", "proxycorporativo")
System.setProperty("http.proxyPort", "8080")
System.setProperty("https.proxyHost", "proxycorporativo")
System.setProperty("https.proxyPort", "8080")

fun data(): Pair<Int, String> {
  var status: Int = -1
  var data: String = ""

  Fuel.get("https://httpbin.org/ipo").response { _, response, _ ->
    status = response.statusCode
    data = String(response.data, Charset.defaultCharset())
  }.join()

  return Pair(status, data)
}

println(data())

