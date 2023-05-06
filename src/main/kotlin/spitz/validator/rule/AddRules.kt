package spitz.validator.rule

import spitz.validator.RuleResult


// 인터페이스 델리게이션
// 인터페이스 선언시 by를 붙여 인스턴스를 넘기면
// 인터페이스의 메서드를 모두 해당 인스턴스가 처리하게 자동으로 코드 생성
class AddRules(block: AddRules.() -> Unit) : MutableSet<Rule> by mutableSetOf() {
    init {
        block(this)
    }

    inner class equal(private val base: Any, private val msg: String?) : Rule {
        override fun check(target: RuleResult): RuleResult {
            return if (target is RuleResult.Value<*> && base == target.value) target
            else RuleResult.fail(msg)
        }

    }

    init {
        this@AddRules += this
    }
}
