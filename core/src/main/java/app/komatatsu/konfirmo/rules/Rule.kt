package app.komatatsu.konfirmo.rules

import android.content.Context
import app.komatatsu.konfirmo.R

data class Rule(val type: ValidationType,
                val threshold: Int? = null,
                var message: String? = null,
                var result: Boolean = false) {

    fun getMessage(context: Context): String {
        if (message != null) return message !!

        when (type) {
            ValidationType.REQUIRED -> {
                return context.getString(R.string.required)
            }
            ValidationType.MAX_LENGTH -> {
                if (threshold == null) throw IllegalArgumentException("threshold is required to set maxLength")
                return context.getString(R.string.max_length, threshold)
            }
            ValidationType.MIN_LENGTH -> {
                if (threshold == null) throw IllegalArgumentException("threshold is required to set minLength")
                return context.getString(R.string.min_length, threshold)
            }
            ValidationType.MAX_LINES -> {
                if (threshold == null) throw IllegalArgumentException("threshold is required to set maxLines")
                return context.getString(R.string.max_lines, threshold)
            }
            ValidationType.FORMAT_NUMBER -> {
                return context.getString(R.string.format_number)
            }
            ValidationType.FORMAT_DATE -> {
                return context.getString(R.string.format_date)
            }
            ValidationType.FORMAT_ZIP -> {
                return context.getString(R.string.format_zip)
            }
        }
    }

    companion object {
        val REQUIRED = Rule(ValidationType.REQUIRED)
        val FORMAT_NUMBER = Rule(ValidationType.FORMAT_NUMBER)
        val FORMAT_DATE = Rule(ValidationType.FORMAT_DATE)
        val FORMAT_ZIP = Rule(ValidationType.FORMAT_ZIP)
    }
}
