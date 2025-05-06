package collection

fun main() {
    val names = listOf("Marta", "Maciek", "Marta", "Daniel")
    println(names.distinctBy { it[0] })
    println(names.distinctBy { it.length })
}

