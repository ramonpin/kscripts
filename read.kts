import java.io.InputStream

fun readAndPrintln(input: InputStream) {
  input.bufferedReader().lines().forEach(::println)
}
    
val p = ProcessBuilder("cat", "/tmp/grande")                                                                
val a = p.start()
readAndPrintln(a.inputStream)
println("Exited with status : ${a.waitFor()}")

