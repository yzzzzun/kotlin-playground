package spitz.validator.rule

sealed interface RuleResult {
    companion object {
        private const val defaultMsg = "invalid"
        fun <T : Any> value(v: T): RuleResult = Value(v)
        fun fail(msg: String?): RuleResult = Fail(msg ?: defaultMsg)
    }

    data class Value<T : Any>(val value: T) : RuleResult
    data class Fail(val msg: String) : RuleResult
}
