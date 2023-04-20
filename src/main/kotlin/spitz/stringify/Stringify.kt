package spitz.stringify

import kotlin.reflect.KProperty


fun main() {
    println(stringify(Json0(3, "abc")))
    println(stringify(Json1(3, "abc", listOf("1", "2", "3"))))
}

fun jsonValue(value: Any?, builder: StringBuilder) {
    when (value) {
        null -> builder.append("null")
        is String -> jsonString(value, builder)
        is Boolean, is Number -> builder.append("$value")
        is List<*> -> jsonList(value, builder)
        else -> jsonObject(value, builder)
    }
}

fun stringify(value: Any): String {
    val builder = StringBuilder()
    jsonValue(value, builder)
    return builder.toString()
}

private fun jsonString(v: String, builder: StringBuilder) = builder.append(""""${v.replace("\"", "\\\"")}"""")
private fun jsonList(target: List<*>, builder: StringBuilder) {
    builder.append('[')
    target.joinTo({ builder.append(',') }) {
        jsonValue(it, builder)
    }
    builder.append(']')
}

private fun <T : Any> jsonObject(target: T, builder: StringBuilder) {
    builder.append('{')
    target::class.members.filterIsInstance<KProperty<*>>()
        .joinTo({ builder.append(',') }) {
            "${jsonValue(it.name, builder)}:${jsonValue(it.getter.call(target), builder)}"
        }
    builder.append('}')
}

fun <T> Iterable<T>.joinTo(sep: () -> Unit, transform: (T) -> Unit) {
    this.forEachIndexed { count, element ->
        if (count != 0) sep()
        transform(element)
    }
}

class Json0(val a: Int, val b: String)
class Json1(val a: Int, val b: String, val c: List<String>)
