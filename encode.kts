val data = "hola mundo!!!"
val salt = "saltsaltsaltsaltsaltsalt".toByteArray()

val encoder = { b1: Byte, b2: Byte -> String.format("%02x", b1.toInt() xor b2.toInt()) }
var dataBytes = data.toByteArray(Charsets.UTF_8)
println("dataBytes ${dataBytes.joinToString("::")}")
var encodedBytes = dataBytes.zip(salt, encoder)
println("encodedBytes $encodedBytes")
var encoded = encodedBytes.joinToString("").reversed()
println("encoded $encoded")

val decoder = { b1: Byte, b2: Byte -> (b1.toInt() xor b2.toInt()).toByte() }
val encodedBytes2 = encoded.reversed().chunked(2, { it })
println("encodedBytes2 $encodedBytes2")
val encodedBytes3 = encodedBytes2.map { Integer.parseInt(it.toString(), 16).toByte() } 
println("encodedBytes3 $encodedBytes3")
val dataBytes2 = encodedBytes3.toByteArray().zip(salt, decoder).toByteArray()
println("dataBytes2 ${dataBytes2.joinToString("::")}")
val res = String(dataBytes2)
println("res: $res")

println("-".repeat(100))

// -------------------------------------------------------------------------
fun encode(data: String, salt: ByteArray): String {
  val encoder = { b1: Byte, b2: Byte -> String.format("%02x", b1.toInt() xor b2.toInt()) }
  val dataBytes = data.toByteArray(Charsets.UTF_8)
  val encodedBytes = dataBytes.zip(salt, encoder)
  return encodedBytes.joinToString("").reversed()
}

fun decode(encoded: String, salt: ByteArray): String {
  val decoder = { b1: Byte, b2: Byte -> (b1.toInt() xor b2.toInt()).toByte() }
  val encodedBytes = encoded.reversed().chunked(2, { Integer.parseInt(it.toString(), 16).toByte() })
  val dataBytes = encodedBytes.toByteArray().zip(salt, decoder).toByteArray()
  return String(dataBytes)
}

val fun_encoded = encode(data, salt)
println("fun_encoded: ${fun_encoded}")
println("fun_decoded: ${decode(fun_encoded, salt)}")

