package inc.evil.playground

fun strLen(s: String) = s.length
fun strLenSafe(s: String?) = s?.length ?: 0

class Address(
    val streetAddress: String, val zipCode: Int,
    val city: String, val country: String
)

class Company(val name: String, val address: Address?)
class Persona(val name: String, val company: Company?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Persona

        if (name != other.name) return false
        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (company?.hashCode() ?: 0)
        return result
    }
}

fun Persona.getCountryName(): String {
    return company?.address?.country ?: "Unknown" //safe-call operator with elvis operator in action
}

fun printShippingLabel(person: Persona) {
    val address = person.company?.address
        ?: throw IllegalArgumentException("No address") //works with throw expressions
    with(address) {
        println(streetAddress)
        println("$zipCode $city, $country")
    }
}

fun sendEmailTo(email: String) {
    println("Sending email to $email")
}


fun ignoreNulls(s: String?) {
    val sNotNull: String = s!! //not null assertion
    println(sNotNull.length)
}

//extension function with safe call operator
fun String?.isNullOrBlank(): Boolean = this == null || this.isBlank()

//T by default is allowed to have null
fun <T> printHashCodeNullabe(t: T) = println(t?.hashCode())

//T is non-null now
fun <T: Any> printHashCodeNotNull(t: T) = println(t.hashCode())

fun main() {
    strLen("string")
//    strLen(null) no allowed
    val address = Address("Baker Street", 1, "London", "England")
    val company = Company("CGK", address)
    val persona = Persona("Mike", company)

    println(persona.getCountryName())
    printShippingLabel(persona)

    val any: Any = persona as? Any ?: throw IllegalArgumentException("Oops") //safe cast operator

    ignoreNulls(null)

    var email: String? = "mike@email.com"
//    sendEmailTo(email) not allowed to pass nullable to not-null
    if (email != null) {
        sendEmailTo(email)
    }

    email?.let { sendEmailTo(it) } //can use let with safe-call operator for it
    persona.company?.let { c -> sendEmailTo("${c.name}@mail.com") }




}