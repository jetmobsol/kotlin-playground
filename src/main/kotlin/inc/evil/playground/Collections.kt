@file:JvmName("CollectionsUtil") //changes file name for java

package inc.evil.playground

import inc.evil.playground.classes.Course
import inc.evil.playground.classes.Person
import java.util.*
import kotlin.collections.ArrayDeque
import inc.evil.playground.ascending as sorted //importing alias

@JvmOverloads //generates overloaded methods for Java
fun <T> Collection<T>.myJoinToString( //public static method in Java
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

const val UNIX_LINE_SEPARATOR = "\n" //only primitives and String
val COURSE: Course = Course(1, "", Person()) // const not allowed
val UNIX_LINE_SEPARATOR_2 = "\n"

fun String.lastChar(): Char = this[this.length - 1] //extension function - add methods to 3rd party classes
fun String.ascending(): String = this.toList().sorted().joinToString("") { it.toString() }
fun String.descending(): String = this.toList().sortedDescending().joinToString("") { it.toString() }

val String.lastChar: Char get() = this[length - 1] //extension property

infix fun String.concatenate(string: String): String = this + string

fun printElements(vararg values: String): Unit {
    values.forEach { println(it) }
}

fun main() {
    val set = hashSetOf(1, 7, 53)
    val list = arrayListOf(1, 7, 53)
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
    val map2 = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
    val strings = listOf(1, 2, 3, 3)
    var arrayDeque = ArrayDeque<String>()
    arrayDeque.addFirst("element")

    var linkedList = LinkedList<String>()
    linkedList.offer("element")

    println(set)
    println(list)
    println(map)
    println(arrayDeque)
    println(linkedList)

    println(set.javaClass)
    println(list.javaClass)
    println(map.javaClass)
    println(map2.javaClass)
    println(strings.javaClass)

    println(strings.last())
    println(strings.max())
    println(
        strings.joinToString(
            separator = ",",
            prefix = "prefix-",
            postfix = "-postfix",
            limit = -1,
            truncated = "999"
        )
    )
    println(strings.myJoinToString("; "))
    var lastChar = "Kotlin".lastChar()
    println(lastChar)
    println("Kotlin".sorted())
    println("Kotlin".descending())

    printElements(*arrayOf("1", "2", "3")) //spread operator

    val anotherMap = mutableMapOf(1 to "one", 7 to "seven", 53 to "fifty-three") //infix call

    val (number, name) = 1 to "one" //destructuring and infix call
    println("$number - $name")
    anotherMap[number] = name

    for ((key, value) in anotherMap) {
        println(key)
        println(value)
    }

    for ((index, element) in linkedList.withIndex()) {
        println("$index - $element")
    }

    val person = Person()
    val (n, age, rc) = person // destructuring
    println(n)
    println(age)
    println(rc)

    var s = "string" concatenate "anotherString" //infix extension function
    println(s)

    set += 222 //set.add(222)
    println(set)
}

private operator fun Person.component1(): String { //to allow destructuring
    return this.name
}
private operator fun Person.component2(): Int { //to allow destructuring
    return this.age
}
private operator fun Person.component3(): String { //to allow destructuring
    return this.race
}
