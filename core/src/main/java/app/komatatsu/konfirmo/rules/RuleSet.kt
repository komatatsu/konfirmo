package app.komatatsu.konfirmo.rules

import android.widget.EditText

data class RuleSet(
    val editText: EditText,
    var rules: ArrayList<Rule> = ArrayList()
) {
    fun addRule(rule: Rule) = apply {
        this.rules.add(rule)
    }
}
