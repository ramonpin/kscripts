//DEPS com.mashape.unirest:unirest-java:1.4.9
import com.mashape.unirest.http.*

println("Starting......")
val location = "http://localhost:5000/"
val res = Unirest.get(location).asString().body
println(res)
println("End......")

