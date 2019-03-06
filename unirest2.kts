//DEPS com.mashape.unirest:unirest-java:1.4.9
//DEPS org.apache.httpcomponents:httpclient:4.5.3
import org.apache.http.*
import com.mashape.unirest.http.*
import org.apache.http.conn.ssl.*
import java.security.cert.*
import org.apache.http.impl.client.*

object TrustAllStrategy: TrustStrategy {
  override fun isTrusted(chain: Array<X509Certificate>, authType: String): Boolean = true
}

fun makeClient(): CloseableHttpClient {
  val builder = SSLContextBuilder()
  builder.loadTrustMaterial(null, TrustAllStrategy)
  val sslsf = SSLConnectionSocketFactory(builder.build())
  val httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
  return httpclient
}

println("Starting......")
val location = "https://api.si.orange.es/daf2/productCatalog/v1/orange/FichaCliente/productSpecification/OSP"
Unirest.setProxy(HttpHost("proxycorporativo", 8080))
Unirest.setHttpClient(makeClient())
val res = Unirest.get(location).asString().body
println(res)
println("End......")
