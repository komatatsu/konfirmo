package app.komatatsu.konfirmo

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Validator {

    fun required(value: CharSequence?): Boolean {
        if (value == null) return false
        return value.isNotEmpty()
    }

    fun maxLength(value: CharSequence?, threshold: Int?): Boolean {
        if (value == null) return true
        threshold?.also {
            if (it >= value.length) {
                return true
            }
        }
        return false
    }

    fun minLength(value: CharSequence?, threshold: Int?): Boolean {
        if (value == null) return false
        if (threshold == null || threshold < value.length) {
            return true
        }
        return false
    }

    private const val NEW_LINE = "\n"
    fun maxLines(value: CharSequence?, threshold: Int?): Boolean {
        if (value == null || threshold == null) return true

        val array = value.split(NEW_LINE.toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        return array.size <= threshold
    }

    private const val DOT = "."
    /**
     * Treats what can be cast as Double or Long as a number.
     * The decimal point corresponds only to the 0.0 format.
     */
    fun formatNumber(value: CharSequence?): Boolean {
        if (value == null) return false

        return if (value.toString().contains(DOT)) {
            if (value.startsWith(DOT)
                || value.endsWith(DOT)
                || value == DOT) {
                //  The decimal point corresponds only to the 0.0 format.
                false
            } else {
                isDouble(value.toString())
            }
        } else {
            Validator.isLong(value.toString())
        }
    }

    private fun isLong(value: String): Boolean {
        return try {
            value.toLong()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isDouble(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Only the format of yyyy-MM-dd and yyyy/MM/dd can be determined.
     */
    fun formatDate(value: CharSequence?): Boolean {
        if (value == null) return false
        return isHyphenStyleDate(value.toString()) || isSlashStyleDate(value.toString())
    }

    private fun isHyphenStyleDate(value: String?): Boolean {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.isLenient = false
            format.parse(value)
            true
        } catch (e: ParseException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }

    private fun isSlashStyleDate(value: String?): Boolean {
        return try {
            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            format.isLenient = false
            format.parse(value)
            true
        } catch (e: ParseException) {
            false
        } catch (e: NullPointerException) {
            false
        }
    }

    /**
     * It corresponds only to Japanese  zip code.
     * Only check the format, it's not check it is a real zip code.
     */
    fun formatZip(value: CharSequence?): Boolean {
        if (value == null) return false
        return value.matches("^[0-9]{3}-[0-9]{4}$".toRegex()) || value.matches("^[0-9]{7}$".toRegex())
    }
}
