package inc.evil.playground

fun main(args: Array<String>) {
    val name: String = "Filip"
    val anotherName = "Bob"
    var age: Int = 34
    var salary = 30000L
    var isEdited = false
    val payRate = 20.01
    var intRange: IntRange = 1..2
    var intRange1: IntRange = IntRange(1, 2)
    println(intRange.sum()
        .apply { println("Apply $this") }
        .run { println("Run $this") }
        .let { println("Let $it") }
        .also { println("Also $it") })

    val multiline = """
        ABC
            DEF
    """.trimIndent()

    println(payRate)
    println("Name is $name with length ${name.length}")
    println(isEdited)
    println(salary)
    println(name)
    println(anotherName)
    println(age)
    println(multiline)

}