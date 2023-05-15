package spitz.validator

interface Validator {
    fun <T : Any> check(v: Any): Result<T>
}
