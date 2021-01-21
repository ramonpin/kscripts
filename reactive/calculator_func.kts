@file:DependsOn("io.reactivex.rxjava2:rxkotlin:2.2.0")

import io.reactivex.subjects.*
import java.util.regex.*

class ReactiveCalculator(a:Int, b:Int) {

  internal val subjectCalc:Subject<ReactiveCalculator> = PublishSubject.create()
  internal var nums:Pair<Int,Int> = Pair(0,0) 
 
  init{ 
    nums = Pair(a,b) 

    subjectCalc.subscribe({ 
      with(it) { 
        calculateAddition() 
        calculateSubstraction() 
        calculateMultiplication() 
        calculateDivision() 
      } 
    })
 
     subjectCalc.onNext(this) 
    }

    inline fun calculateAddition():Int {
      val result = nums.first + nums.second
      println("Add = $result")
      return result
    }

    inline fun calculateSubstraction():Int {
      val result = nums.first - nums.second
      println("Substract = $result")
      return result
    }

    inline fun calculateMultiplication():Int {
      val result = nums.first * nums.second
      println("Multiply = $result")
      return result
    }

    inline fun calculateDivision():Double {
      val result = (nums.first*1.0) / (nums.second*1.0)
      println("Multiply = $result")
      return result
    }

    inline fun modifyNumbers (a:Int = nums.first, b: Int = nums.second) {
      nums = Pair(a,b)
      subjectCalc.onNext(this)
    }
 
   fun handleInput(inputLine:String?) { 

    if(!inputLine.equals("exit")) { 
        val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")
 
        var a: Int? = null 
        var b: Int? = null 
 
        val matcher: Matcher = pattern.matcher(inputLine) 
 
        if (matcher.matches() && matcher.group(1) != null &&  matcher.group(2) != null) {
          if(matcher.group(1).toLowerCase() == "a") {
             a = matcher.group(2).toInt()
          } else if(matcher.group(1).toLowerCase() == "b") {
             b = matcher.group(2).toInt()
          }
        } 
 
        when { 
          a != null && b != null -> modifyNumbers(a, b) 
          a != null -> modifyNumbers(a = a) 
          b != null -> modifyNumbers(b = b) 
          else -> println("Invalid Input") 
       } 
    } 
  } 
} 

println("Initial Out put with a = 15, b = 10")
var calculator:ReactiveCalculator = ReactiveCalculator(15,10)
println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")

var line:String = ""
do {
  line = readLine()!!
  calculator.handleInput(line)
} while (line != null && !line.toLowerCase().contains("exit"))

