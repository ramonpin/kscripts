//DEPS com.typesafe:config:1.3.2
import com.typesafe.config.*
import java.io.*

fun Config.toFlatStringMap(): Map<String, String> {
  val ret = mutableMapOf<String, String>()
  this.entrySet().forEach { ret[it.key] = this.getAnyRef(it.key).toString() }
  return ret
}

val conf1 = ConfigFactory.parseFile(File("/home/ramon/tmp/fich1.conf"))
val conf2 = ConfigFactory.parseFile(File("fich2.conf")).withFallback(conf1)

println(conf1.getValue("prop_2").origin())
println(conf2.getValue("prop_2").origin())
println(conf2)

val conf3 = ConfigFactory.parseFile(File("/home/ramon/tmp/prueba.conf"))
println(conf3.getValue("encoded").origin().filename())

