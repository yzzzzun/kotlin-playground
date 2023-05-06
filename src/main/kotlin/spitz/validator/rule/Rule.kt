package spitz.validator.rule

import spitz.validator.RuleResult

interface Rule {
    fun check(target: RuleResult): RuleResult
}
