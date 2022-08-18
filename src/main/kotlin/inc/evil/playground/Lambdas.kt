package inc.evil.playground

import java.io.File
import java.time.LocalDate

data class Person(val name: String, val age: Int) {
    fun getEmail(): String {
        return "$name-${LocalDate.now().year - age}@mail.com"
    }

    fun getUsername(): String {
        return "$name-${LocalDate.now().year - age}"
    }
}

fun findTheOldest(people: List<Person>): Unit {
    var maxAge = 0;
    var theOldest: Person? = null
    for (person in people) {
        if (person.age > maxAge) {
            maxAge = person.age
            theOldest = person
        }
    }
    println(theOldest)
}

fun printMessagesWithPrefix(messages: Collection<Person>, prefix: String) {
    messages.forEach {
        println("$prefix $it") //access outer var
    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++ //modify not final outer vars
        } else if (it.startsWith("5")) {
            serverErrors++ //modify not final outer vars
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

fun Person.isOld(): Boolean = age > 65

val methodRef = ::findTheOldest //top-level method ref no class specified
val constructorRef = ::Person //top-level constructor ref
val memberRef = Person::age
val infixFunctionRef = Person::isOld

class Book(val title: String, val authors: List<String>)

fun postponeComputation(delay: Int, computation: Runnable) {
    Thread.sleep(delay.toLong())
    println("Waited $delay millis")
    computation.run()
}

//lambda with receiver
fun buildString(builderAction: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}

//This special type is called the receiver type, and the
//value of that type passed to the lambda becomes the receiver object.
fun Customero.configureCustomer(customerAction: Customero.() -> Unit): Customero {
    this
    this.customerAction()
    return this
}

fun main() {
    val customer = Customero(26, "name")
    customer
        .configureCustomer { setAttribute("data", "some info") }
        .also { println(it.data) }


    println(buildString {
        append("a")
        append("b")
    }.toString())


    val people = listOf<Person>(Person("Alice", 29), Person("Bob", 31))
    println(people.maxBy(Person::age)) //method reference
    println(people.minBy { it.age }) //lambda
    println(people.minBy { p: Person -> p.age }) //lambda
    println(people.reduceRight { _, acc -> acc })
    println(people.asReversed())
    println(people.joinToString(separator = ". ", postfix = ".") { person -> person.name })
    people.forEach { println(it) }
    printMessagesWithPrefix(people, "prefix")
    printProblemCounts(listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error"))
    println(constructorRef("name", 15)) //call to constructor ref
    println(methodRef(people)) //call to method ref
    var person = Person("Mike", 15)
    var boundRef = person::name //bound ref to
    println(memberRef(person))
    println(infixFunctionRef(Person("Mike", 67)))

    println(listOf(1, 2, 3, 4).filter { it % 2 == 0 }.map { it * it })
    var maxBy = people.maxBy(Person::age) //to not be repeated, computed only once
    var onlyMaxAge = people.filter { it.age == maxBy.age }
    var ageFifteen = people.all { it.age == 15 }
    var count = people.count()
    var noneAgeFifteen = people.none { it.age == 15 }
    var single = people.filter { it.isOld() }.singleOrNull()
    var find = people.find { it.isOld() }
    var groupBy: Map<Int, List<Person>> = people.groupBy { p -> p.age }
    println(listOf<String>("a", "anb", "bc", "cd").groupBy(String::first))
    val books = listOf<Book>(Book("Thus Spoke Zarathustra", listOf("F. Nietzsche")))
    println(books.flatMap { it.authors }.toSet())
    println(listOf<List<String>>(listOf("a"), listOf("b")).flatten())

    people.asSequence()
        .map(Person::name)
        .filter { it.startsWith("A") }
        .toList()

    people.asSequence()
        .map(Person::name)
        .filter { print("$it, "); it.startsWith("A") } //another method call with ;
        .toList()

    generateSequence(1) { it + 1 }
        .take(5)
        .forEach { println(it) }

    generateSequence(1) { it + 1 }
        .takeWhile { it <= 55 }
        .filter { it % 2 == 0 }
        .map {
            Person(
                "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,q,w,y,x,z".split(",")
                    .shuffled()
                    .take(5)
                    .joinToString(""), it
            )
        }
        .filter { p -> p.name.startsWith("a") }
        .forEach { println(it) }

    val file = "C:\\Users\\ionpa\\Projects\\kotlin-playground\\src\\main\\kotlin\\inc\\evil\\playground\\Lambdas.kt"
    println(
        File(file)
            .isDirectory
    )
    File(file)
        .useLines { it.take(5).toList() }
        .forEach { println(it) }

    generateSequence(File(file)) { it.parentFile }
        .filter { it.name.startsWith("k") }
        .forEach { println(it) }

    postponeComputation(1000) { println("Hey") }
    postponeComputation(100, object : Runnable {
        override fun run() {
            println("Hey")
        }
    })

    var computation = object : Runnable {
        override fun run() {
            println("Hey")
        }
    }
    postponeComputation(100, computation)

    fun alphabet() = with(StringBuilder()) {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
        toString()
    }

    println(with(Person("Mike", 16)) {//lambdas with receivers/scope functions
        println(getEmail()) //using this
        println(getUsername())
    }) //return Unit

    println(Person("Mike", 16).run {//lambdas with receivers/scope functions
        println(this.getEmail()) // using this
        println(this.getUsername())
    }) //returns unit

    println(Person("Mike", 16).apply {//lambdas with receivers/scope functions
        println(getEmail()) // using this
        println(getUsername())
    }) //returns this

    println(Person("Mike", 16).also {//lambdas with receivers/scope functions
        println(it.getEmail()) // using it
        println(it.getUsername())
    }) //returns this

    println(Person("Mike", 16).let {//lambdas with receivers/scope functions
        println(it.getEmail()) // using it
        println(it.getUsername())
    }) //returns unit

}
