package lambda

import kotlin.contracts.contract

data class Student(
    val name: String,
    val age: Int,
    val polint: Int,
)

fun main() {


    val students = listOf(
        Student("Alice", 20, 90),
        Student("Bob", 22, 85),
        Student("Charlie", 21, 95),
    )

    val points = students.fold(0) { acc, student ->
        acc + student.polint
    }
    println("Total points: $points")

    doWork(
        { println("Inline block1") },
        { println("Non-inline block2") }
    )

    printTypeName<Int>()
    printTypeName<String>()
}

inline fun doWork(
    block1: () -> Unit,
    noinline block2: () -> Unit
) {
    block1()
    val copy = block2
    copy()
}

inline fun doWork(crossinline block: () -> Unit) {
    val runnable = Runnable {
        block() // return 금지
    }
    runnable.run()
}

inline fun < reified T> printTypeName(){
    print(T::class.simpleName)
}
