package inc.evil.playground

fun main() {
    val name = "Alex"

    if (name.length == 4) {
        println("yay")
    }

    val result = if (name.length == 4) {
        println("yay")
        name
    } else {
        println("nay")
        name
    }

    println(result)

    val position = 1;

//    var medal = if (position == 1) {
//        "gold"
//    } else if (position == 2) {
//        "silver"
//    } else if (position == 3) {
//        "bronze"
//    } else {
//        "no medal"
//    }

    val medal = when (position) {
        1 -> "gold"
        2 -> "silver"
        3 -> "bronze"
        else -> "no medal"
    }

    println(medal)

}