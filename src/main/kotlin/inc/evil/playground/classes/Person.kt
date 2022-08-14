package inc.evil.playground.classes

class Person(
    var name: String = "Unknown",
    var age: Int = 1
) {
    var race: String = ""
        get() = field
        set(value) {
            println("Race is being set")
            field = value
        }

    init {
        println("I am becoming alive, and my name is $name")
    }

    constructor(race: String) : this() { //secondary constructor
        this.race = race
    }

    constructor(age: Int) : this() {
        this.age = age
    }

    fun sayHello(): Unit {
        println("hello I'm ${this.name}")
    }

    override fun toString(): String {
        return "Person(name='$name', age=$age)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (name != other.name) return false
        if (age != other.age) return false
        if (race != other.race) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        result = 31 * result + race.hashCode()
        return result
    }


}


fun main() {
    var person = Person("Mike", 25)
    var person2 = Person("European")
    var person3 = Person(1)
    var person4 = Person(name = "John")
    person2.sayHello()
    person.name = "Alex"
    person.race = "Mongo"
    person.sayHello()
}