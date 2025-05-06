package collection

fun main(){
    val numbers = listOf(1,2,3,4,5)
    numbers.scan(0) { acc, i ->
        acc + i
    }

    val last = numbers.runningFold(0) { acc, i ->
        acc + i
    }.forEach(::println)
    println(last)

    numbers.foldRight(0) { i, acc ->
        i + acc
    }
}