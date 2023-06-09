package spitz.validator.rule

class RuleDSL(block: RuleDSL.() -> Unit) {

    private val cases = mutableSetOf<AddRules>()
    fun Case(block: AddRules.() -> Unit) {
        cases += AddRules(block)
    }

    fun check(v: Any): RuleResult {
        var result = RuleResult.value(v)
        cases.any {
            result = RuleResult.value(v)
            it.all { rule ->
                result = rule.check(result)
                when (result) {
                    is RuleResult.Value<*> -> true
                    is RuleResult.Fail -> false
                }
            }
        }
        return result
    }

    init {
        block()
    }
}
