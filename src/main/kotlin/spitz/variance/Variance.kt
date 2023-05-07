package spitz.variance

//Default 무공변
// Tree<out T>(val value: T)
// val tree:Tree<Number> = Tree<Int>(10) !!error!!

//공변
class Tree<out T>(val value: T)

//반공변
class Node<in T : Number>(private val value: T, private val next: Node<T>? = null) {
    operator fun contains(target: T): Boolean {
        return if (value.toInt() == target.toInt()) true else next?.contains(target) ?: false
    }
}

fun main() {
    val node: Node<Int> = Node<Number>(8.0)
    node.contains(8)

    val tree: Tree<Number> = Tree<Int>(10)
    tree.value.toDouble()
}

