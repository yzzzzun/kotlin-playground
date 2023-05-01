package spitz.jsonparser

import spitz.stringify.Name
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation

fun <T : Any> parseJson(target: T, json: String): T? {
    val lexer = JsonLexer(json)
    lexer.skipWhite()
    return parseObject(lexer, target)
}

// 확장 함수 형태로 제공
fun <T : Any> T.fromJson(json: String): T? = parseJson(this, json)

fun <T : Any> parseObject(lexer: JsonLexer, target: T): T? {
    if (!lexer.isOpenObject()) return null
    lexer.next()
    val props = target::class.members
        .filterIsInstance<KMutableProperty<*>>()
        .associate {
            (it.findAnnotation<Name>()?.name ?: it.name) to it
        }
    while (!lexer.isCloseObject()) {
        lexer.skipWhite()
        val key = lexer.key() ?: return null
        val prop = props[key] ?: return null
        val value = jsonValue(lexer, prop.returnType) ?: return null
        prop.setter.call(target, value)
        lexer.skipWhite()
        if (lexer.isComma()) lexer.next()
    }
    lexer.next()
    return target
}

inline fun jsonValue(lexer: JsonLexer, type: KType): Any? {
    return when (val klass = type.classifier as? KClass<*> ?: return null) {
        String::class -> lexer.string()
        Int::class -> lexer.int()
        Long::class -> lexer.long()
        Float::class -> lexer.float()
        Double::class -> lexer.double()
        Boolean::class -> lexer.boolean()
        List::class -> parseList(
            lexer, type.arguments[0].type?.classifier as? KClass<*> ?: return null
        )
        else -> parseObject(lexer, klass.createInstance())
    }
}

fun parseList(lexer: JsonLexer, klass: KClass<*>): Any? {
    if (!lexer.isOpenArray()) return null
    lexer.next()
    val result = mutableListOf<Any>()
    val type = klass.createType()

    while (!lexer.isCloseArray()) {
        lexer.skipWhite()
        val v = jsonValue(lexer, type) ?: return null
        result += v
        lexer.skipWhite()
        if (lexer.isComma()) lexer.next()
    }
    lexer.next()
    return result
}
