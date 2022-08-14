package inc.evil.playground

fun main() {
    checkRange(1)
    checkRange(-1)
}

private fun checkRange(number: Int) {
    val percentage =
        if (number in 0..100)
            number
        else
            throw IllegalArgumentException(
                "A percentage value must be between 0 and 100: $number"
            )
}