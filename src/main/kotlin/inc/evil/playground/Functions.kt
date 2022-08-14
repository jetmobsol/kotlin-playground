package inc.evil.playground

import java.time.LocalDate

fun printPersonDetails(name: String, email: String, dob: LocalDate = LocalDate.now()) {
    println("$name $email $dob")
}

fun main() {
    printPersonDetails("Mike", "Scott") //default value parameters
    printPersonDetails("Mike", "Scott", LocalDate.of(1995, 2, 3))
    printPersonDetails(name = "Mike", dob = LocalDate.of(1995, 2, 3), email = "Scott") //named parameter
    topLevelFunction(SOME_CONSTANT);
}