package inc.evil.playground

import java.lang.IndexOutOfBoundsException

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

data class Rectangle(val upperLeft: Point, val lowerRight: Point){
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

operator fun ClosedRange<Point>.iterator(): Iterator<Point>{
    return object : Iterator<Point>{
        var current = start;
        override fun hasNext(): Boolean = current <= endInclusive
        override fun next(): Point = current++
    }


}

fun main() {
    var point = Point(1, 2) + Point(2, 3)
    var anotherPoint = Point(4, 2) + Point(9, 3)
    println(point)
    var (x, y) = Point(1, 2) // destructuring

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
    for (point in closedRange) { //iterator implementation
        println(point)
    }

}

