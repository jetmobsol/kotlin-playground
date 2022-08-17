package inc.evil.playground

val <T> List<T>.penultimate: T get() = this[size - 2]
fun <T> List<T>.mySlice(indices: IntRange): List<T> {
    val result: MutableList<T> = ArrayList<T>()
    indices.forEach { result.add(this[it]) }
    return result
}

fun <T> List<T>.myFilter(predicate: (T) -> Boolean): List<T> {
    return this.filter(predicate)
}

interface MyList<T> {
    operator fun get(index: Int): T
}

class StringList : MyList<String> {
    override fun get(index: Int): String {
        TODO("Not yet implemented")
    }
}

fun List<Int>.mySum(): Int {
    var sum = 0
    this.forEach { sum += (it) }
    return sum;
}

fun <T : Number> oneHalf(value: T): Double { //upper-bound to Number
    return value.toDouble() / 2.0
}

fun <T> ensureTrailingPeriod(seq: T) where T : CharSequence, T : Appendable { //constraints
    if (!seq.endsWith('.')) {
        seq.append('.')
    }
}

class ProcessorNullable<T> { //by default Any?
    fun process(value: T) {
        value?.hashCode()
    }
}

class ProcessorNonNullable<T : Any> {
    fun process(value: T) {
        value.hashCode()
    }
}

fun printSum(c: Collection<*>) {
    val intList = c as? List<Int>
        ?: throw IllegalArgumentException("List is expected")
    println(intList.sum())
}

inline fun <reified T> isA(value: Any) = value is T


inline fun <reified T> List<Any>.filter(): List<T> {
    return this.filter { it is T }.map { it as T }
}

class Service {
    inline fun <reified T> getClassName(): String = T::class.java.simpleName

    companion object {
        fun <T> load(java: Class<T>) {
            TODO("Not yet implemented")
        }
    }
}

inline fun <reified T> Service.loadService() {
    return Service.load(T::class.java)
}

fun printNumbers(values: List<Number>) {
    values.forEach { println(it) }
}

open class Animal {
    fun feed() {
        println("Feeding")
    }
}

class Cat : Animal() {
    fun cleanLitter() {
        println("Cleaning litter")
    }
}

class Herd<out T : Animal>(vararg _animals: T) { //covariance
    val size: Int = 0
        get() = field
    private val animals = _animals

    operator fun get(i: Int): T {
        return animals[i]
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
        feedAll(cats) //covariance
    }
}

interface Function1<in P, out R> { //covariance and contravariance
    operator fun invoke(p: P): R
}

fun <T : R, R> copyData(
    source: MutableList<T>,
    destination: MutableList<R>
) {
    for (item in source) {
        destination.add(item)
    }
}

interface Producer<out T> { // Covariant
    fun produce(): T
}

interface Consumer<in T> { // Contravariant
    fun consume(t: T);
}

open class A(val foo: Int){
    override fun toString(): String = "A(foo=$foo)"
}
class B(foo: Int) : A(foo){
    override fun toString(): String = "B(foo=$foo)"
}

class ProducerA: Producer<A>{
    override fun produce(): A {
        return A(1)
    }
}

class ProducerB: Producer<B>{
    override fun produce(): B {
        return B(1)
    }
}

class ConsumerA: Consumer<A> {
    override fun consume(t: A) {
        println(t)
    }
}

class ConsumerB: Consumer<B> {
    override fun consume(t: B) {
        println(t)
    }
}

fun main() {
    val letters = ('a'..'z').toList()
    println(listOf(1, 2, 3).mySum())
    println(listOf(1, 2, 3).sum())

    val listOf: List<Comparable<*>> = listOf("none", 2, "bob")
    println(listOf.filterIsInstance<String>())

    println(isA<String>("string"))
    println(isA<String>(1))

    println(letters.mySlice(0..2))
    println(letters.penultimate)
    println(letters.myFilter { it.isLetter() })

    val s: Service = Service()
    println(s.getClassName<String>())
    println(listOf.filter<String>())
//    Service().loadService<String>()

    val values: List<Int> = listOf(1, 2, 3)
    printNumbers(values) //covariance in action, Int is Number => List<Int> is List<Number>
    printNumbers(listOf(1.0, 2.0, 3.0)) //covariance in action, Double is Number => List<Double> is List<Number>
//    printNumbers(listOf("a", "b"))

    val list: MutableList<*> = mutableListOf('a', 1, "qwe") //star projection
    println(list)

    val producerOfB: Producer<B> = ProducerB()
    val producerOfA: Producer<A> = producerOfB;  // now legal
// producerOfB = producerOfA;  // still illegal
    println(producerOfB.produce())
    println(producerOfA.produce())

    val consumerOfA: Consumer<A> = ConsumerA()
    val consumerOfB: Consumer<B> = consumerOfA;  // now legal
    consumerOfA.consume(B(1))
    consumerOfA.consume(A(1))
// consumerOfA = consumerOfB;  // still illegal
}