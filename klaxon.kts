@file:DependsOn("com.beust:klaxon:3.0.1")

import com.beust.klaxon.*

val jsonData = StringBuilder("""{
     "_id" : { "oid" : "593440eb7fa580d99d1abe85"} , 
     "name" : "Firstname Secondname" ,
     "reg_number" : "ATC/DCM/1016/230" ,
     "oral" : 11 ,
     "oral_percent" : 73 , 
     "cat_1" : 57 , 
     "cat_2" : 60 , 
     "cat_average" : 59 , 
     "assignment" : 90
}""")

val json: JsonObject = Parser().parse(jsonData) as JsonObject

class Pepe(m: Map<String, String>): Map<String, String> by m {

  fun nombre() = m["nombre"]

}

println(json)

