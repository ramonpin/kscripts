//DEPS org.apache.hadoop:hadoop-common:2.6.0
//DEPS org.apache.hadoop:hadoop-hdfs:2.6.0
//DEPS net.engio:mbassador:1.3.1
//DEPS io.arrow-kt:arrow-data:0.5.5
//--DEPS io.arrow-kt:arrow-syntax:0.5.5
//--DEPS io.arrow-kt:arrow-typeclasses:0.5.5
//--DEPS io.arrow-kt:arrow-instances:0.5.5
import arrow.*
import arrow.data.*

open class GeneralException: Exception()

class NoConnectionException: GeneralException()

class AuthorizationException: GeneralException()

fun checkPermissions() {
    throw AuthorizationException()
}

fun getLotteryNumbersFromCloud(): List<String> {
    throw NoConnectionException()
}

fun getLotteryNumbers(): List<String> {
    checkPermissions()
    return getLotteryNumbersFromCloud()
}

val lotteryTry = Try { getLotteryNumbers() }
println(lotteryTry)
println(lotteryTry.getOrDefault { emptyList() })
println(lotteryTry.getOrElse { ex: Throwable -> emptyList() })
println(lotteryTry.getOrElse { emptyList() })

