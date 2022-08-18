package inc.evil.playground

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.io.BufferedReader
import java.io.FileReader
import java.lang.IndexOutOfBoundsException
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import java.util.function.BiPredicate
import kotlin.concurrent.withLock
import kotlin.reflect.KProperty

class Point(var x: Int, var y: Int) : Comparable<Point> {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)

    //    operator fun plusAssign(other: Point): Unit {
//        x += other.x
//        y += other.y
//    }
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
    operator fun times(value: Int): Point = Point(x * value, y * value)

    //unary
    operator fun inc(): Point = Point(x + 1, y + 1)
    operator fun dec(): Point = Point(x - 1, y - 1)
    operator fun unaryMinus(): Point = Point(-x, -y)

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    override fun compareTo(other: Point): Int {
        return compareValuesBy(this, other, Point::x, Point::y)
    }

    operator fun get(index: Int): Int {
        return when (index) {
            0 -> x
            1 -> y
            else -> {
                throw IndexOutOfBoundsException("Invalid coordinate $index")
            }
        }
    }

    operator fun invoke(index: Int): Int {
        return when (index) {
            0 -> x
            1 -> y
            else -> {
                throw IndexOutOfBoundsException("Invalid coordinate $index")
            }
        }
    }

    operator fun set(index: Int, value: Int): Unit {
        when (index) {
            0 -> x = value
            1 -> y = value
            else -> {
                throw IndexOutOfBoundsException("Invalid coordinate $index")
            }
        }
    }

    override fun toString(): String = "Point(x=$x, y=$y)"
}

data class Rectangle(val upperLeft: Point, val lowerRight: Point) {
    operator fun contains(p: Point): Boolean {
        return p.x in upperLeft.x until lowerRight.x &&
                p.y in upperLeft.y until lowerRight.y
    }
}

//extension function
operator fun Int.times(point: Point): Point {
    return Point(this * point.x, this * point.y)
}

//extensions function operator overloading
operator fun Person.plus(other: Person): Person {
    return Person(this.name + other.name, this.age + age)
}

operator fun ClosedRange<Point>.iterator(): Iterator<Point> {
    return object : Iterator<Point> {
        var current = start;
        override fun hasNext(): Boolean = current <= endInclusive
        override fun next(): Point = current++
    }
}

data class NameComponents(val name: String, val extension: String)

fun splitFilename(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1])
}


class Delegate(var propValue: String, val changeSupport: PropertyChangeSupport) {

    operator fun getValue(thisRef: Customero, property: KProperty<*>): String {
        return ('a'..'z').take(5).shuffled().joinToString("")
    }

    operator fun setValue(thisRef: Customero, property: KProperty<*>, value: String) {
        changeSupport.firePropertyChange(property.name, propValue, value)
        propValue = value
    }

}

class Customero(age: Int, name: String) : PropertyChangeAware() {
    //property change aware via delegation
    var name: String by Delegate(name, changeSupport)
    val emails by lazy { loadEmails(this) }

    //manual property change aware
    var age: Int = age
        set(value) {
            changeSupport.firePropertyChange("age", field, value)
            field = value
        }

    private val _attributes = hashMapOf<String, String>()
    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    val data: String by _attributes

    private fun loadEmails(customero: Customero): List<Email> {
        println("I'm being called")
        return listOf(Email("mike@mail.com"))
    }

}

data class Email(val address: String)

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)
    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

//higher-order functions
fun twoAndThree(operation: (Int, Int) -> Int) {
    val result = operation(2, 3)
    println("The result is $result")
}

fun twoAndThreeReturn(): (Int, Int) -> Int {
    return { a, b -> a + b }
}

fun String.filter(predicate: (Char) -> Boolean): String {
    val sb = StringBuilder()
    for (index in 0 until length) {
        val element = get(index)
        if (predicate(element)) sb.append(element)
    }
    return sb.toString()
}

data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
)

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID }


fun List<SiteVisit>.averageDurationFor(os: OS) =
    filter { it.os == os }.map(SiteVisit::duration).average()

fun main() {
    var point = Point(1, 2) + Point(2, 3)
    var anotherPoint = Point(4, 2) + Point(9, 3)
    println(point)
    var (x, y) = Point(1, 2) // destructuring

    println(point(0))
    println(point(1))

    val person = Person("Mike", 14) + Person("Bob", 15)
    println(person)
    println(point * 2)
    println(2 * point)
    println(++point)
    println(--point)
    point += point
    println(point)

    println(point > anotherPoint) //comparable overloading
    println(point.compareTo(anotherPoint) == 1)
    println(point < anotherPoint)
    println(point.compareTo(anotherPoint) == -1)

    while (point <= anotherPoint) {
        println(point)
        point++
    }

    println(point[0]) //get overloading
    println(point[1])
    point[0] = 22 //set overloading
    println(point[0])
    println(point)

    val rectangle = Rectangle(anotherPoint, point)
    println(rectangle)
    println(--point in rectangle)

    val closedRange: ClosedRange<Point> = anotherPoint..point
    for (p in closedRange) { //iterator implementation
        println(p)
    }

    //destructuring
    val (name, extension) = splitFilename("example.kt")
    println(name)
    println(extension)

    //delegating
    val customero = Customero(13, "bimbo")
    customero.addPropertyChangeListener { evt -> println(evt) }
    customero.age = 15
    println(customero.name)
    customero.name = "mike"

    //lazy delegating
    println(customero.emails)
    println(customero.emails)
    println(customero.emails)
    println(customero.emails)
    println(customero.emails)

    customero.setAttribute("data", "description")
    println(customero.data)

    twoAndThree { a, b -> a + b }
    println("aba789#4c".filter { it in 'a'..'z' })

    val log = listOf(
        SiteVisit("/", 34.0, OS.WINDOWS),
        SiteVisit("/", 22.0, OS.MAC),
        SiteVisit("/login", 12.0, OS.WINDOWS),
        SiteVisit("/signup", 8.0, OS.IOS),
        SiteVisit("/", 16.3, OS.ANDROID)
    )
    println(log.averageDurationFor(OS.MAC))


    val l: Lock = ReentrantLock()
    l.withLock { println() } //Kotlin idiomatic lock access, instead of try/finally

    //Kotlin idiomatic resource management instead of try/finally
    BufferedReader(FileReader("")).use { //inline function
        println(it.readLine());
    }
}


