package app.komatatsu.konfirmo

import org.junit.Assert
import org.junit.Test


class ValidatorTest {

    @Test
    fun required() {
        Assert.assertTrue(Validator.required("a"))

        Assert.assertFalse(Validator.required(""))
        Assert.assertFalse(Validator.required(null))
        Assert.assertFalse(Validator.required(String()))
    }

    @Test
    fun maxLength() {
        // test of value
        Assert.assertTrue(Validator.maxLength(null, 2))
        Assert.assertTrue(Validator.maxLength("", 2))
        Assert.assertTrue(Validator.maxLength("a", 2))
        Assert.assertTrue(Validator.maxLength("aa", 2))

        Assert.assertFalse(Validator.maxLength("aaa", 2))

        // test of threshold
        Assert.assertFalse(Validator.maxLength("aaa", null))
        Assert.assertFalse(Validator.maxLength("aaa", 0))
    }

    @Test
    fun minLength() {
        // test of value
        Assert.assertFalse(Validator.minLength(null, 2))
        Assert.assertFalse(Validator.minLength("", 2))
        Assert.assertFalse(Validator.minLength("a", 2))
        Assert.assertFalse(Validator.minLength("aa", 2))

        Assert.assertTrue(Validator.minLength("aaa", 2))

        // test of threshold
        Assert.assertTrue(Validator.minLength("aaa", null))
        Assert.assertTrue(Validator.minLength("aaa", 0))
    }

    @Test
    fun lessLinesThan() {
        // test of value
        Assert.assertTrue(Validator.maxLines(null, 2))
        Assert.assertTrue(Validator.maxLines("", 2))
        Assert.assertTrue(Validator.maxLines("a", 2))
        Assert.assertTrue(Validator.maxLines("a\na", 2))

        Assert.assertFalse(Validator.maxLines("a\na\na", 2))

        // test of threshold
        Assert.assertTrue(Validator.maxLines("aaa", null))
        Assert.assertFalse(Validator.maxLines("aaa", 0))
        Assert.assertFalse(Validator.maxLines("", - 1))
    }

    @Test
    fun formatNumber() {
        Assert.assertFalse(Validator.formatNumber(null))
        Assert.assertFalse(Validator.formatNumber(""))
        Assert.assertFalse(Validator.formatNumber("a"))
        Assert.assertFalse(Validator.formatNumber("."))
        Assert.assertFalse(Validator.formatNumber(".5"))
        Assert.assertFalse(Validator.formatNumber("10."))
        Assert.assertFalse(Validator.formatNumber("-"))

        Assert.assertTrue(Validator.formatNumber("1"))
        Assert.assertTrue(Validator.formatNumber("１"))
        Assert.assertTrue(Validator.formatNumber("1.2"))
        Assert.assertTrue(Validator.formatNumber("0.222222"))
        Assert.assertTrue(Validator.formatNumber("-5"))
    }

    @Test
    fun formatDate() {
        Assert.assertFalse(Validator.formatDate(null))
        Assert.assertFalse(Validator.formatDate(""))
        Assert.assertFalse(Validator.formatDate("a"))
        Assert.assertFalse(Validator.formatDate("2019.11.11"))

        Assert.assertTrue(Validator.formatDate("2019-11-11"))
        Assert.assertTrue(Validator.formatDate("2019/11/11 "))
        Assert.assertTrue(Validator.formatDate("2019-11-11 11:11:11"))
    }

    @Test
    fun formatZip() {
        Assert.assertFalse(Validator.formatZip(null))
        Assert.assertFalse(Validator.formatZip(""))
        Assert.assertFalse(Validator.formatZip("a"))
        Assert.assertFalse(Validator.formatZip("00-4204"))
        Assert.assertFalse(Validator.formatZip("000-42"))

        Assert.assertTrue(Validator.formatZip("0000000"))
        Assert.assertTrue(Validator.formatZip("000-0000"))
    }
}
