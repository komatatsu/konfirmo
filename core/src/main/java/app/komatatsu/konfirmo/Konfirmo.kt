package app.komatatsu.konfirmo

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import app.komatatsu.konfirmo.configrations.Separator
import app.komatatsu.konfirmo.rules.Rule
import app.komatatsu.konfirmo.rules.RuleSet
import app.komatatsu.konfirmo.rules.ValidationType
import com.google.android.material.textfield.TextInputLayout

class Konfirmo(val context: Context,
               private var saveButton: Button? = null,
               private var ruleList: ArrayList<RuleSet> = ArrayList()) {

    fun addSaveButton(button: Button) = apply {
        this.saveButton = button
    }

    fun addRuleSet(ruleSet: RuleSet) = apply {
        this.ruleList.add(ruleSet)
    }

    fun initialize() {
        for (ruleSet in ruleList) {
            setUpRule(ruleSet)
        }
        checkCanSave()
    }

    fun checkCanSave() {
        if (saveButton == null) return

        var result = true
        for (ruleSet in ruleList) {
            var childCanSave = true
            for (rule in ruleSet.rules) {
                if (! rule.result) {
                    childCanSave = false
                    break
                }
            }
            if (! childCanSave) {
                result = false
                break
            }
        }
        saveButton?.isEnabled = result
    }

    private fun setUpRule(ruleSet: RuleSet) {
        val layout = getTextInputLayout(ruleSet.editText)
        ruleSet.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var totalMessage = ""
                for (rule in ruleSet.rules) {
                    if (validate(s, rule)) {
                        rule.result = true
                    } else {
                        if (TextUtils.isEmpty(totalMessage)) {
                            totalMessage = rule.getMessage(context)
                        } else {
                            totalMessage += getSeparators() + rule.getMessage(context)
                        }
                        rule.result = false
                    }
                }
                if (TextUtils.isEmpty(totalMessage)) {
                    clearErrorMessage(ruleSet.editText, layout)
                } else {
                    setErrorMessage(ruleSet.editText, layout, totalMessage)
                }
                checkCanSave()
            }
        })
    }

    private fun validate(text: CharSequence?, rule: Rule): Boolean {
        return when (rule.type) {
            ValidationType.REQUIRED -> {
                Validator.required(text)
            }
            ValidationType.MAX_LENGTH -> {
                Validator.maxLength(text, rule.threshold)
            }
            ValidationType.MIN_LENGTH -> {
                Validator.minLength(text, rule.threshold)
            }
            ValidationType.MAX_LINES -> {
                Validator.maxLines(text, rule.threshold)
            }
            ValidationType.FORMAT_NUMBER -> {
                Validator.formatNumber(text)
            }
            ValidationType.FORMAT_DATE -> {
                Validator.formatDate(text)
            }
            ValidationType.FORMAT_ZIP -> {
                Validator.formatZip(text)
            }
        }
    }

    private fun getTextInputLayout(editText: EditText): TextInputLayout? {
        return if (editText.parent.parent is TextInputLayout) editText.parent.parent as TextInputLayout else null
    }

    private fun setErrorMessage(editText: EditText, layout: TextInputLayout?, message: String) {
        if (layout == null) {
            editText.error = message
        } else {
            layout.error = message
        }
    }

    private fun clearErrorMessage(editText: EditText, layout: TextInputLayout?) {
        editText.error = null
        if (layout != null) {
            layout.error = null
            layout.isErrorEnabled = false
        }
    }

    private fun getSeparators(separator: Separator = Separator.NL): String {
        return when (separator) {
            Separator.COMMA -> {
                ", "
            }
            Separator.NL -> {
                "\n"
            }
        }
    }
}
