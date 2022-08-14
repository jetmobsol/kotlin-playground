package inc.evil.playground

import java.util.ArrayList
import java.util.TreeMap

fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i "
}

fun main() {
    for (i in 1..100){
        print(fizzBuzz(i))
    }
    println()
    for (i in 100 downTo 1 step 2){
        print(fizzBuzz(i))
    }


    val binaryReps = TreeMap<Char, String>()
    for(c in 'A'..'F'){
        binaryReps[c] = Integer.toBinaryString(c.code)
    }
    binaryReps.computeIfAbsent('c') { c -> Integer.toBinaryString(c.code) }
    binaryReps.forEach { entry ->
        println("${entry.key} - ${entry.value}")
    }
    for ((key, value) in binaryReps) {
        println("${key} - ${value}")
    }

    val arrayList: ArrayList<String> = arrayListOf<String>("10", "11", "1001")
    for ((index, s) in arrayList.withIndex()) {
        println("$index - $s")
    }

    println('c' in 'a'..'b')
    println('a' in 'a'..'z')
    println('a' !in '1'..'5')

    fun recognize(c: Char) = when (c) {
        in '0'..'9' -> "It's a digit!"
        in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
        else -> "I don't know…"
    }

    println(recognize('c'))

    val listOf: List<String> = listOf("Java", "Scala")
    println("Kotlin" in listOf)
}