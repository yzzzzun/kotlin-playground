package collection

import org.junit.Assert.assertEquals
import org.junit.Test

//fun <T> List<T>.plusAt(index: Int, element: T): List<T> {
//    val take = this.take(index)
//    val takeLast = this.takeLast(this.size - index)
//    return when {
//        index < 0 || index > this.size -> throw IllegalArgumentException("Illegal index $index")
//        index == 0 -> listOf(element) + this
//        index == this.size -> this + element
//        else -> take + element + takeLast
//    }
//}

fun <T> List<T>.plusAt(index: Int, element: T): List<T> {
    require(index in 0..this.size) { "Illegal index $index" }
    return this.take(index) + element + this.drop(index)
}

//fun <T> List<T>.plusAt(index: Int, element: T): List<T> {
//    require(index in 0..this.size) { "Illegal index $index" }
//    return when (index) {
//        0 -> listOf(element) + this
//        this.size -> this + element
//        else -> flatMapIndexed { i, item ->
//            if (i == index) listOf(element, item) else listOf(item)
//        }
//    }
//}

fun main() {
    val list = listOf(1, 2, 3)
    println(list.plusAt(1, 4)) // [1, 4, 2, 3]
    println(list.plusAt(0, 5)) // [5, 1, 2, 3]
    println(list.plusAt(3, 6)) // [1, 2, 3, 6]

    val list2 = listOf("A", "B", "C")
    println(list2.plusAt(1, "D")) // [A, D, B, C]
}

class PlusAtTest {

    @Test
    fun `Simple addition to the middle adds correctly at the position`() {
        assertEquals(listOf(1, 2, 7, 3), listOf(1, 2, 3).plusAt(2, 7))
        assertEquals(listOf("A", "B", "D", "C"), listOf("A", "B", "C").plusAt(2, "D"))
    }

    @Test
    fun `When we add at size position, element is added at the end`() {
        assertEquals(listOf(1, 2, 3, 7), listOf(1, 2, 3).plusAt(3, 7))
        assertEquals(listOf("A", "B", "C", "D"), listOf("A", "B", "C").plusAt(3, "D"))
    }

    @Test
    fun `When we add at 0, element is added at the beginning`() {
        assertEquals(listOf(7, 1, 2, 3), listOf(1, 2, 3).plusAt(0, 7))
        assertEquals(listOf("D", "A", "B", "C"), listOf("A", "B", "C").plusAt(0, "D"))
    }

    @Test
    fun `When we try to insert at illegal position, IllegalArgumentException error is thrown`() {
        assertThrows<IllegalArgumentException> { listOf(1, 2, 3).plusAt(-1, 7) }
        assertThrows<IllegalArgumentException> { listOf(1, 2, 3).plusAt(8, 7) }
        assertThrows<IllegalArgumentException> { listOf(1, 2, 3).plusAt(10, 7) }
        assertThrows<IllegalArgumentException> { listOf(1, 2, 3).plusAt(100, 7) }
    }
}

inline fun <reified T: Throwable> assertThrows(operation: () -> Unit) {
    val result = runCatching { operation() }
    assert(result.isFailure) { "Operation has not failed with exception" }
    val exception = result.exceptionOrNull()
    assert(exception is T) { "Incorrect exception type, it should be ${T::class}, but it is $exception" }
}