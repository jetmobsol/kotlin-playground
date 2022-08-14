package inc.evil.playground.classes

data class Employee(val id: Int, val name: String) {
}

fun main() {
    var employee = Employee(1, "Bob")
    var employee1 = Employee(2, "Bob")
    println(employee == employee1)
    println(employee)

}