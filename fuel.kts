@file:DependsOn("com.github.kittinunf.fuel:fuel:2.0.1")
@file:DependsOn("com.github.kittinunf.fuel:fuel-coroutines:2.0.1")

import com.github.kittinunf.fuel.Fuel
import java.nio.charset.Charset

System.setProperty("http.proxyHost", "proxycorporativo")
System.setProperty("http.proxyPort", "8080")
System.setProperty("https.proxyHost", "proxycorporativo")
System.setProperty("https.proxyPort", "8080")

Fuel.get("https://httpbin.org/ip").response { _, response, _ ->
  println("status: ${response.statusCode}")
  println(String(response.data, Charset.defaultCharset()))
}.join()
