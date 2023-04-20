package spitz.stringify

import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation


fun main() {
    println(stringify(Json0(3, "abc")))
    println(stringify(Json1(3, "abc", listOf("1", "2", "3"))))
    println(stringify(Json2(3, "abc")))
}

fun StringBuilder.jsonValue(value: Any?) {
    when (value) {
        null -> append("null")
        is String -> jsonString(value)
        is Boolean, is Number -> append("$value")
        is List<*> -> jsonList(value)
        else -> jsonObject(value)
    }
}

fun stringify(value: Any) = StringBuilder().run {
    jsonValue(value)
    toString()
}

private fun StringBuilder.jsonString(v: String) = append(""""${v.replace("\"", "\\\"")}"""")
private fun StringBuilder.jsonList(target: List<*>) {
    wrap('[', ']') {
        target.joinTo(::comma) {
            jsonValue(it)
        }
    }
}

private fun <T : Any> StringBuilder.jsonObject(target: T) {
    wrap('{', '}') {
        target::class.members.filterIsInstance<KProperty<*>>()
            .filter { it.findAnnotation<Ex>() == null }
            .joinTo(::comma) {
                jsonValue(it.findAnnotation<Name>()?.name ?: it.name)
                append(":")
                jsonValue(it.getter.call(target))
            }
    }
}

fun <T> Iterable<T>.joinTo(sep: () -> Unit, transform: (T) -> Unit) {
    this.forEachIndexed { count, element ->
        if (count != 0) sep()
        transform(element)
    }
}

fun StringBuilder.comma() {
    append(',')
}

fun StringBuilder.wrap(begin: Char, end: Char, block: StringBuilder.() -> Unit) {
    append(begin)
    block()
    append(end)
}

class Json0(val a: Int, val b: String)
class Json1(val a: Int, val b: String, val c: List<String>)
class Json2(@Ex val a: Int, @Name("title") val b: String)

@Target(AnnotationTarget.PROPERTY)
annotation class Ex

@Target(AnnotationTarget.PROPERTY)
annotation class Name(val name: String)
