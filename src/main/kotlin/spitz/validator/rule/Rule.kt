package spitz.validator.rule

interface Rule {
    fun check(target: RuleResult): RuleResult
}
