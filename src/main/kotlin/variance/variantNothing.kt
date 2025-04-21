package variance

sealed class LinkedList<out T>

data class Node<T> (
    val head: T,
    val tail: LinkedList<T>,
) : LinkedList<T>()

// 공변으로 LinkedList<Nothing> 는 모든 LinkedList의 서브타입이됨
// Nothing이 모든 타입의 서브타입이기 때문
object Empty: LinkedList<Nothing>()

fun main() {
    val stars = Node("A", Node("B",Empty))
    val ints = Node(1, Node(2,Empty))
    val empty: LinkedList<Char> = Empty
}
