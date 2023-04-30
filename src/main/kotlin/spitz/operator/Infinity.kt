package spitz.operator

import kotlin.reflect.KProperty

//값객체 선언 -> 불변 속성 -> 복사본 할당
data class Infinity<T>(
    private val value: T,
    private val limit: Int = -1,
    private val nextValue: (T) -> T,
) {
    class Iter<T>(private var item: Infinity<T>) : Iterator<T> {
        override fun hasNext() = item.limit != 0
        override fun next(): T {
            val result by item //속성 델리게이터
            item = item.next()
            return result
        }
    }

    operator fun iterator() = Iter(this)
    operator fun getValue(ref: Any?, prop: KProperty<*>) = value
    fun next() = Infinity(nextValue(value), limit - 1, nextValue)
}

fun main(args: Array<String>) {
    val a = Infinity(0, 20) { it + 1 }
    var limit = 20
    for (i in a) {
        println("$i")
    }
}
