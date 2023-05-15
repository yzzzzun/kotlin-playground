package spitz.validator

import spitz.validator.rule.length
import spitz.validator.rule.trim

fun main() {
    val vali = RuleValidator {
        Case {
            trim()
            length(5, "not length 5")
        }
    }
    vali.check<String>("abcde")
        .onSuccess {
            println("ok $it")
        }
        .onFailure {
            println("fail ${it.message}")
        }
}
