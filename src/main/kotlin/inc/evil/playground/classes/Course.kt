package inc.evil.playground.classes

data class Course(val id: Int, val name: String, val author: Person) {
}

fun main() {
    val course = Course(1, "Course 101", Person(name = "Bob"))
    val course1 = Course(1, "Course 101", Person(name = "Bob"))
    println(course)
    println(course == course1)
    println(course === course1)
    println(course.equals(course1))
}