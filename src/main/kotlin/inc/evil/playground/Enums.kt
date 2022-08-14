package inc.evil.playground

import inc.evil.playground.Color.*

enum class Color(val r: Int, val g: Int, val b: Int) {
    YELLOW(1, 2, 3),
    GREEN(1, 2, 3),
    BLACK(2, 4, 5),
    RED(1, 2, 3);

    infix fun and(c: Color): Color = BLACK

}

fun getMnemonic(c: Color): String {
    return when (c) {
        YELLOW -> "Yellow"
        GREEN -> "Green"
        BLACK -> "Black"
        RED -> "Red"
    }
}

fun mix(c1: Color, c2: Color): Color =
    when (setOf(c1, c2)) {
        setOf(RED, YELLOW) -> BLACK
        else -> throw RuntimeException()
    }

fun mixOptimized(c1: Color, c2: Color) =
    when {
        (c1 == RED && c2 == YELLOW) ||
                (c1 == YELLOW && c2 == RED) ->
            BLACK

        else -> {
            throw RuntimeException()
        }
    }

fun main() {
    var color = RED and YELLOW
    println(color)
    println(getMnemonic(color))
    println(mix(RED, YELLOW))
}