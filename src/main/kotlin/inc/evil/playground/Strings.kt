package inc.evil.playground

fun parsePath(path: String): Unit {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast("/")
    val extension = fullName.substringBeforeLast("/")

    println("Dir: $directory, name: $fileName, ext: $extension")
}

fun parsePathWithRegex(path: String) {
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(path)
    if (matchResult != null) {
        val (directory, filename, extension) = matchResult.destructured
        println("Dir: $directory, name: $filename, ext: $extension")
    }
}

fun main() {
    "1.2.3.4.5.5.6.6.54.6.34.65.3456.345.".split(".").forEach { println(it) }
    "1029(342$#!d23$!2345%#%^!asdfhjsda32?$".split("[!?]".toRegex()).forEach { println(it) }
    parsePath("/Users/yole/kotlin-book/chapter.adoc")
    val kotlinLogo = """
                        | //
                        |//
                        |/ \"""
   println(kotlinLogo.trimIndent())
}