//DEPS com.typesafe:config:1.3.2
import com.typesafe.config.*
import java.io.*

val conf = ConfigFactory.parseFile(File("local.conf"))
println("prop_local  : ${conf.getValue("prop_local").origin()}")
println("prop_import : ${conf.getValue("prop_import").origin()}")

