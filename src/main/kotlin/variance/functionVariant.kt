package variance

fun printProcessedNumber(transformation: (Int) -> Any){
    println(transformation(42))
}

fun main() {
    val intToDouble: (Int) -> Double = { it.toDouble() }
    printProcessedNumber(intToDouble)

    val numberAsString: (Number) -> String = { it.toString() }
    printProcessedNumber(numberAsString)

    val indentity: (Int) -> Int = { it }
    printProcessedNumber(indentity)

    val numberToInt: (Number) -> Int = { it.toInt() }
    printProcessedNumber(numberToInt)

    val numberHash:(Any) -> Number = { it.hashCode() }
    printProcessedNumber(numberHash)
}