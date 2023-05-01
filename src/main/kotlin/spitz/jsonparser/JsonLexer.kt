package spitz.jsonparser

class JsonLexer(
    val json: String,
) {
    val last = json.lastIndex
    var cursor = 0
        private set
    inline val curr get() = json[cursor]
    fun next() {
        if (cursor < last) ++cursor
    }

    inline fun skipWhite() {
        while ("\t\n\r".indexOf(curr) != -1 && cursor < last) next()
    }

    inline fun isOpenObject(): Boolean = '{' == curr
    inline fun isCloseObject(): Boolean = '}' == curr
    inline fun isOpenArray(): Boolean = '[' == curr
    inline fun isCloseArray(): Boolean = ']' == curr
    inline fun isComma() = curr == ','

    inline fun key(): String? {
        val result = string() ?: return null
        skipWhite()
        if (curr != ':') return null
        next()
        skipWhite()
        return result
    }

    inline fun string(): String? {
        if (curr != '"') return null
        next()
        val start = cursor
        var isSkip = false
        while (isSkip || curr != '"') {
            isSkip = if (isSkip) false else curr == '\\'
            next()
        }
        val result = json.substring(start, cursor)
        next()
        return result
    }

    inline fun number(): String? {
        val start = cursor
        while ("-.012345689".indexOf(curr) != -1) next()
        return if (start == cursor) null else json.substring(start, cursor)
    }

    inline fun int() = number()?.toInt()
    inline fun long() = number()?.toLong()
    inline fun double() = number()?.toDouble()
    inline fun float() = number()?.toFloat()
    fun boolean(): Boolean? {
        return when {
            json.substring(cursor, cursor + 4) == "true" -> {
                cursor += 4
                true
            }
            json.substring(cursor, cursor + 5) == "false" -> {
                cursor += 5
                false
            }
            else -> null
        }
    }
}
