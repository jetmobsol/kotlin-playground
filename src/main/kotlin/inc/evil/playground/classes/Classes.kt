package inc.evil.playground.classes

class Hippo
class Giraffe
class Bear
class Dog(_name: String) {
    var name = _name
        private set
    fun bark(): String = "$name says: Yip!"
}

class Cat {
    val name = "Sparkles"
    fun meow(): String = "mrrow!"
}

class MutableNameAlien(var name: String)
class FixedNameAlien(val name: String)

class AlienSpecies(
    val name: String,
    val eyes: Int,
    val hands: Int,
    val legs: Int
) {
    fun describe() =
        "$name with $eyes eyes, " +
                "$hands hands and $legs legs"

    override fun toString(): String {
        return "AlienSpecies(name='$name', eyes=$eyes, hands=$hands, legs=$legs)"
    }
}

class User(val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() { //extension function with local function inside
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                "Can't save user $id: empty $fieldName")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser(user: User) {
    user.validateBeforeSave()
}

fun main() {
    var hippo = Hippo()
    val giraffe = Giraffe()
    val bear = Bear()
    val dog = Dog("Peter")
    val cat = Cat()
    val mutableNameAlien = MutableNameAlien("Ete")
    val fixedNameAlien = FixedNameAlien("Eteeee")
    var alienSpecies = AlienSpecies("Bob", 2, 4, 2)
    println(alienSpecies.describe())
    println(alienSpecies.toString())

    println(mutableNameAlien.name)
    println(fixedNameAlien.name)

    println(cat.meow())
    println(dog.bark())
    println(hippo)
    println(giraffe)
    println(bear)
}