package inc.evil.playground

import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun readNumbers(reader: BufferedReader): List<Int?> { //nullable return type argument
    val result = ArrayList<Int?>()
    for (line in reader.lineSequence()) {
        try {
            val number = line.toInt()
            result.add(number)
        } catch (e: NumberFormatException) {
            result.add(null)
        }
    }
    return result
}

fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (number in numbers) {
        if (number != null) {
            sumOfValidNumbers += number
        } else {
            invalidNumbers++
        }
    }
    println("Sum of valid numbers: $sumOfValidNumbers")
    println("Invalid numbers: $invalidNumbers")
}

fun addValidNumbersFilterNotNull(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull() //api method
    println("Sum of valid numbers: ${validNumbers.sum()}")
    println("Invalid numbers: ${numbers.size - validNumbers.size}")
}

//immutable vs muttable
fun <T> copyElements(source: Collection<T>, target: MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

fun main() {
    val path = "C:\\Users\\ionpa\\Projects\\kotlin-playground\\src\\main\\resources\\number.txt"
    FileInputStream(File(path)).use { fis ->
        InputStreamReader(fis).use { isr ->
            BufferedReader(isr).use { br ->
                val ints = readNumbers(br)
                println(ints)
                addValidNumbers(ints)
                addValidNumbersFilterNotNull(ints)
            }
        }
    }

    val source: Collection<Int> = arrayListOf(3, 5, 7)
    val target: MutableCollection<Int> = arrayListOf(1)
    copyElements(source, target)
    println(target)

    //not allowed, immutable
    val listOf: List<Int> = listOf(1, 2, 3) //immutable
    println(listOf.javaClass) //Arrays$ArrayList
    val mapOf: Map<Int, String> = mapOf(1 to "one") //immutable
    println(mapOf.javaClass) //Collections$SingletonMap
    val set: Set<Int> = setOf(1, 2, 3) //immutable
    println(set.javaClass) //LinkedHashSet
//    listOf(1, 2, 3)[0] = 3
//    mapOf(1 to "one")[1] = "one"
//    setOf(1, 2, 3).add()

    val mutableListOf: MutableList<Int> = mutableListOf(1, 2, 3) //mutable
    println(mutableListOf.javaClass) //ArrayList
    mutableListOf[0] = 1
    val mutableMapOf: MutableMap<Int, String> = mutableMapOf(1 to "one") //mutable
    println(mutableMapOf.javaClass) //LinkedHashMap
    mutableMapOf[1] = "one"
    val mutableSetOf: MutableSet<Int> = mutableSetOf(1, 2, 3) //mutable
    println(mutableSetOf.javaClass) //LinkedHashSet
    mutableSetOf.add(4)

    val arrayOf: Array<Int> = arrayOf(1, 2, 3)
    val arrayOfNulls: Array<Int?> = arrayOfNulls<Int>(5)
    val array: Array<Int> = Array<Int>(5) { i -> i + 1 * i }
    println(arrayOf.javaClass) // [Ljava.lang.Integer - java.lang.Integer[]
    val intArrayOf: IntArray = intArrayOf(1, 2, 3)
    println(intArrayOf.javaClass) //primitve array int[]
    val intArray = IntArray(5)
    println(intArray.contentToString())

    println(array.contentToString())
    arrayOf.forEachIndexed { index, i -> println("$index $i") }
    for (i in arrayOf.indices){
        println("$i - ${arrayOf[i]}")
    }
    println(('a'..'z').shuffled().take(5).joinToString(""))
    println(listOf.toTypedArray().contentToString())
    println("%s/%s/%s".format(*arrayOf)) //spread operator

}