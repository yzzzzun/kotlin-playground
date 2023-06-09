package spitz

const val number = """([+-]?[.\d]+)"""
val groupMD = """$number([*/])$number""".toRegex()
val plus = number.toRegex()
val trim = """[^.\d-+*/()]""".toRegex();
var bracket = """\(([^)]+)\)""".toRegex()

private fun removeBracket(v: String): String {
    var str = v.replace(trim, "")
    while (bracket.containsMatchIn(str)) str = str.replace(bracket) { "${calc(it.groupValues[1])}" }
    return str
}

fun calc(v: String): Double {
    var str = removeBracket(v)
    while (groupMD.containsMatchIn(str)) {
        str = str.replace(groupMD) {
            val (_, left, op, right) = it.groupValues
            val leftValue = left.toDouble()
            val rightValue = right.toDouble()
            "${
                when (op) {
                    "*" -> leftValue * rightValue
                    "/" -> leftValue / rightValue
                    else -> throw Throwable("invalid operator $op")
                }
            }"
        }
    }
    return plus.findAll(str).fold(0.0) { acc, curr -> acc + curr.groupValues[1].toDouble() }
}






