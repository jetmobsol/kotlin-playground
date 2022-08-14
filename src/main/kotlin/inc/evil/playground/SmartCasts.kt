package inc.evil.playground

interface Expression
class Num(val value: Int) : Expression
class Sum(val left: Expression, val right: Expression) : Expression
class Subtract(val left: Expression, val right: Expression) : Expression
class Division(val left: Expression, val right: Expression) : Expression
class Multiplication(val left: Expression, val right: Expression) : Expression

fun eval(e: Expression): Int =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        is Subtract -> eval(e.right) - eval(e.left)
        is Division -> eval(e.right) / eval(e.left)
        is Multiplication -> eval(e.right) * eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }


fun main() {
    println(eval(Sum(Sum(Num(1), Num(2)), Num(3))))
    println(eval(Subtract(Subtract(Num(1), Num(2)), Num(3))))
    println(eval(Multiplication(Sum(Num(1), Num(2)), Num(3))))
    println(eval(Multiplication(Division(Num(1), Num(2)), Num(3))))
}