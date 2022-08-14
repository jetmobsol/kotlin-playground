package inc.evil.playground

import inc.evil.playground.classes.Person
import sun.tools.jconsole.AboutDialog
import sun.tools.jconsole.JConsole
import java.awt.Component
import java.awt.Window
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

open class View {
    open fun click(): Unit = println("view clicked")
    fun getCurrentState(): State = TODO("Provide the return value")
    fun restoreState(state: State) = Unit
}

class Button : View() {
    override fun click() = println("button clicked")
    class ButtonState() : State {
        fun getState(): Unit {
//            click() //not allowed
        }
    }// by default as static class from Java

    inner class AnotherButtonState() : State {
        fun getState(): Unit {
            this@Button.showOff() //reference this from outer
            click() //allowed
        }
    }// by default as simle inner class from Java
}

interface State {

}

fun View.showOff() = println("I'm a view!")
fun Button.showOff() = println("I'm a button!")

interface Clickable {
    var title: String
    val description: String

    //    const val MAX_VAL: Int = 1 not allowed
    abstract fun click()
    fun showOff() = println("I'm clickable")
}

interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if (b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}

class Menu(override var title: String, override val description: String) : Clickable, Focusable {
    override fun click() {
        println("Something got clicked")
    }

    override fun toString(): String {
        return "Menu(title='$title', description='$description')"
    }

    override fun showOff() {
        super<Clickable>.showOff() //multi-interface implementation resolution
        super<Focusable>.showOff()
    }
}

//properties are open by default, “override” without “final” implies being open
open class RichButton(
    final override var title: String,
    override val description: String
) : Clickable { //allowed to be subclassed
    final override fun click() = println("I'm clicked") // overridden
    open fun animate() {} //allowed to be overridden
    fun disable() {}
}

open class SaveButton(title: String, description: String) : RichButton(title, description) {
    override val description: String
        get() = super.description

    override fun animate() {
        println("Saving is loading")
    }
}

open class SaveNoConfirmationButton(title: String, description: String) : SaveButton(title, description) {
    final override val description: String
        get() = super.description

    final override fun animate() {
        super.animate()
    }
}

abstract class Animated { //abstract classes
    abstract fun animate()
    open fun stopAnimating() {}
    fun animateTwice() {}
}

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

//extension function
//fun TalkativeButton.giveSpeech() { public tries to expose internal
//    yell() tries to access private
//    whisper() tries to access protected
//}

class UserNoExplicitConstructor(val nickname: String)

class UserSemiExplicitConstructor(_nickname: String) {
    val nickname = _nickname
}

class UserExplicitConstructor constructor(_nickname: String) {
    val nickname: String

    init {
        nickname = _nickname
    }
}

class Secretive private constructor() {} // private constructor

open class Pane { // multiple constructors

    constructor(ctx: Context, attr: AttributeSet) {
// some code
    }

    constructor(ctx: Context) : this(ctx, AttributeSet()) { //calling other constructor
// ...
    }
}

class MyButton : Pane {
    constructor(ctx: Context) : super(ctx) {
// ...
    }

    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr) {
// ...
    }
}

class AttributeSet {

}

class Context {

}

interface User {
    val email: String
    val nickname: String //needs to be provided in implementors
    val username: String get() = email.substringBefore("@")
}

class PrivateUser(
    override val nickname: String,
    override val email: String
) : User

class SubscribingUser(override val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@')
}

class FacebookUser(val accountId: Int, override val email: String) : User {
    override val nickname = getFacebookName(accountId)
        get() = field.toString()

    private fun getFacebookName(accountId: Int): String {
        TODO("Not yet implemented")
    }
}

data class Client(
    val name: String,
    val postalCode: Int
) // data classes with toString, equals, hashcode, copy, componentN

//decorator pattern in action by Kotlin! Just Wow...
class CountingSet<T>(val delegatee: MutableCollection<T> = HashSet<T>()) : MutableCollection<T> by delegatee {
    var addInvocations: Int = 0;

    override fun add(element: T): Boolean {
        addInvocations++
        return delegatee.add(element)
    }
}

object Payroll { //singleton in action
    val allEmployees = arrayListOf<Person>(Person(name = "bob"))
    fun calculateSalary() {
        for (person in allEmployees) {
            println("${person.name} - ${ThreadLocalRandom.current().nextInt(1000)}")
        }
    }
}

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(file1: File, file2: File): Int {
        return file1.path.compareTo(
            file2.path,
            ignoreCase = true
        )
    }
}

interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Guy(val name: String) {
    object NameComparator : Comparator<Person> {
        override fun compare(p1: Person, p2: Person): Int =
            p1.name.compareTo(p2.name)
    }

    companion object : JSONFactory<Guy> { // implement interface
        fun bar() {
            println("Companion object called")
        }

        override fun fromJSON(jsonText: String): Guy {
            TODO("Not yet implemented")
        }
    }

    object SimpleObject {
        fun bar() {
            println("Object called")
        }
    }
}

fun Guy.Companion.toJSON(): String = "" //nameless companion object function extension
fun Guy.SimpleObject.toJSON(): String = "" //companion object function extension

//factory in action
class Customer private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
            Customer(email.substringBefore('@'))

        fun newFacebookUser(accountId: Int) =
            Customer(getFacebookName(accountId))

        private fun getFacebookName(accountId: Int): String {
            TODO("Not yet implemented")
        }
    }
}

fun main() {
    var button = Button()
    button.click();
    button.showOff();
    var view: View = Button()
    view.click()
    view.showOff();

    val menu = Menu("Main menu", "Some description")
    menu.click()
    menu.showOff()
    println(menu)
    println(UserExplicitConstructor("Bob").nickname)

    var client = Client("Bob", 2008)
    var client2 = Client("Bob", 2008)
    val (name, postalCode) = Client("Bob", 2008)
    println(client.hashCode() == client2.hashCode())
    println(client == client2)
    println(client)
    println(client2)
    var copyOfClient = client.copy()
    println(copyOfClient)
    println(copyOfClient == client)
    println(copyOfClient === client)
    val copyOfCopyOfClient = copyOfClient.copy("Mike")
    println(copyOfCopyOfClient)

    val countingSet = CountingSet<String>()
    countingSet.add("A")
    countingSet.add("B")
    countingSet.add("C")
    println(countingSet)
    println(countingSet.addInvocations)

    Payroll.calculateSalary() //singleton in action

    println(CaseInsensitiveFileComparator.compare(File("/User"), File("/user")))
    var guy = Guy("Mike")
    Guy.bar() // like static in java
    Guy.toJSON()
    Guy.SimpleObject.bar()

    AboutDialog(JConsole(false))
        .addMouseListener(object : MouseAdapter() { // object expression
            override fun mouseClicked(e: MouseEvent?) {
                super.mouseClicked(e)
            }
        })

    fun countClicks(window: Window) {
        var clickCount = 0
        window.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                clickCount++ //can modify non-final outer fields
            }
        })
    }

    val newFacebookUser = Customer.newFacebookUser(123)
}