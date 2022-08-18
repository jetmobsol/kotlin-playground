package inc.evil.playground

import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) {
    println(index)
}

@Retention(AnnotationRetention.RUNTIME) // default retention is runtime
@Target(AnnotationTarget.ANNOTATION_CLASS) //meta-annotation
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding

@Target(AnnotationTarget.PROPERTY)
annotation class JsonName(val value: String)

@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude

//Accepts any class implementing ValueSerializer, not only ValueSerializer::class
//Allows ValueSerializer to serialize any values
@Target(AnnotationTarget.PROPERTY)
annotation class CustomSerializer(val serializerKClass: KClass<out ValueSerializer<*>>)

//The out keyword specifies that you’re allowed to refer to classes that
//extend Any, not just to Any itself.
@Target(AnnotationTarget.PROPERTY)
annotation class DeserializeInterface(val value: KClass<out Any>)

interface Organization {
    val name: String
}

data class OrganizationImpl(override val name: String) : Organization

interface ValueSerializer<T> {
    fun toJsonValue(value: T): Any?
    fun fromJsonValue(jsonValue: Any?): T
}

class DateSerializer : ValueSerializer<LocalDate> {
    override fun toJsonValue(value: LocalDate): Any? {
        return value.toString()
    }

    override fun fromJsonValue(jsonValue: Any?): LocalDate {
        val s = jsonValue?.toString() ?: LocalDate.MIN.toString()
        return LocalDate.parse(s)
    }
}


data class Man(
    @JsonName("fname") val firstName: String? = null,
    @JsonExclude val age: Int? = null,
    @DeserializeInterface(OrganizationImpl::class) val company: Organization,
    @CustomSerializer(serializerKClass = DateSerializer::class) val birthDate: LocalDate
)

fun main() {

    val man = Man("bob", 16, OrganizationImpl("rocket"), LocalDate.of(1992, 1, 1))
    val kClass = man.javaClass.kotlin
    println(kClass.simpleName)
    println(kClass.members)
    println(kClass.memberProperties)

    val properties = kClass.memberProperties
        .filter { it.findAnnotation<JsonExclude>() != null }
        .map { it.findAnnotation<JsonExclude>() }
        .also { println(it) }
    println(properties)

    val jsonNameProperties = kClass.memberProperties
        .filter { it.findAnnotation<JsonName>() != null }
        .map { it: KProperty1<Man, *> -> it.findAnnotation<JsonName>().also { println(it?.value) } }
        .also { it: List<JsonName?> -> println(it) }
    println(jsonNameProperties)

    val jsonName: KProperty1<Man, *> = kClass.memberProperties.asSequence()
        .filter { it.name == "firstName" }
        .first()
    println(jsonName)

    val birthDate: KProperty1<Man, *> = kClass.memberProperties.asSequence()
        .filter { it.name == "birthDate" }
        .first()
    val serializer: CustomSerializer? = birthDate.findAnnotation<CustomSerializer>()
    val valueSerializerKClass: KClass<out ValueSerializer<*>>? = serializer?.serializerKClass
    val valueSerializer =
        (valueSerializerKClass?.objectInstance ?: valueSerializerKClass?.createInstance()) as ValueSerializer<Any?>
    val toJsonValue = valueSerializer.toJsonValue(birthDate.get(man))
    println(toJsonValue)


    val jsonNameAnnotation = jsonName.findAnnotation<JsonName>()
    println(jsonNameAnnotation?.value)

    man.javaClass.fields
        .filter { it.isAnnotationPresent(JsonName::class.java) }
        .map { it.getAnnotation(JsonName::class.java) }
        .also { println(it) }

    man.javaClass.declaredFields.forEach { println(it.annotations.toList()) }
}
