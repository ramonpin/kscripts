@file:MavenRepository("geaosp", "https://torredecontrol.si.orange.es/nexus/repository/gea-osp-group/")
@file:DependsOn("com.orange.bigdata:mirinda:1.13.0")

import orange.mirinda.encode.*

val originalConf    = args[0]
val destinationConf = args[1]
val encodedValue    = args[2]

val originalSalt    = generateSalt(originalConf)
val destinationSalt = generateSalt(destinationConf)

val decodedValue    = decode(encodedValue, originalSalt) 
val reencodedValue  = encode(decodedValue, destinationSalt)

println(decodedValue)
println(reencodedValue)

