package com.mason.resizecalculator.ui.calculator

import org.junit.Test

import org.junit.Assert.*

class CalculatorFeatureTest {
    private var calculatorFeature = CalculatorFeature()

    @Test
    fun `test inputNumber with 1 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(0)
        val state = calculatorFeature.inputNumber(1)

        assertEquals("1", state.result)
        assertNotEquals("1.0", state.result)
        assertNotEquals("0", state.result)

        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(0)
        val result = calculatorFeature.calculate().result
        assertEquals("1", result)
    }

    @Test
    fun `test inputNumber with 123 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        val state = calculatorFeature.inputNumber(3)

        assertEquals("123", state.result)
        assertNotEquals("123.0", state.result)
    }

    @Test
    fun `test plus 12345 99999 with toggleSign True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        val input = calculatorFeature.inputNumber(5).result

        assertEquals("12345", input)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        val result1 = calculatorFeature.calculate().result

        assertEquals("112344", result1)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        val result2 = calculatorFeature.calculate().result

        assertEquals("1235789", result2)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(9)
        calculatorFeature.toggleSign()
        calculatorFeature.inputNumber(9)
        val result3 = calculatorFeature.calculate().result

        assertEquals("-10", result3)
    }


    @Test
    fun `test plus 123456789 987654321 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(8)
        val input = calculatorFeature.inputNumber(9).result

        assertEquals("123456789", input)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(1)
        val result1 = calculatorFeature.calculate().result
        assertEquals("1111111110", result1)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        val result2 = calculatorFeature.calculate().result
        assertEquals("1111112097", result2)

    }

    @Test
    fun `test minus -10000 99999 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        val input = calculatorFeature.inputNumber(0).result

        assertEquals("-10000", input)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        val result1 = calculatorFeature.calculate().result

        assertEquals("-109999", result1)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        val result2 = calculatorFeature.calculate().result

        assertNotEquals("-100000", result2)
        assertEquals("-119998", result2)
    }

    @Test
    fun `test minus 12345 99999 with toggleSign True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        val input = calculatorFeature.inputNumber(5).result

        assertEquals("12345", input)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        val result1 = calculatorFeature.calculate().result

        assertEquals("-87654", result1)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        val result2 = calculatorFeature.calculate().result

        assertEquals("-1211099", result2)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(9)
        calculatorFeature.toggleSign()
        calculatorFeature.inputNumber(9)
        val result3 = calculatorFeature.calculate().result

        assertEquals("24700", result3)
    }


    @Test
    fun `test multiply 12345_6789 Multiply 0_987654321 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(9)

        calculatorFeature.inputOperation(MULTIPLY)

        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(2)
        val state = calculatorFeature.inputNumber(1)
        assertEquals("0.987654321", state.result)

        val result = calculatorFeature.calculate().result
        assertEquals("12193.2631112635", result)
    }

    @Test
    fun `test multiply 09999 plus 9_9 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(9)
        val state = calculatorFeature.inputNumber(9)
        assertEquals("9999", state.result)
        calculatorFeature.inputOperation(MULTIPLY)
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(9)
        val result = calculatorFeature.calculate().result
        assertEquals("98990.1", result)
    }

    @Test
    fun `test multiply 123 3 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputOperation(MULTIPLY)
        calculatorFeature.inputNumber(3)
        val result = calculatorFeature.calculate().result

        assertEquals("369", result)
    }

    @Test
    fun `test multiply 987 3 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputOperation(DIVIDE)
        calculatorFeature.inputNumber(3)
        val result = calculatorFeature.calculate().result

        assertEquals("329", result)
    }

    @Test
    fun `test multiply 987 2 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputOperation(DIVIDE)
        calculatorFeature.inputNumber(2)
        val result = calculatorFeature.calculate().result

        assertNotEquals("493.50", result)
        assertNotEquals("493", result)
        assertEquals("493.5", result)
    }

    @Test
    fun `test multiply 33 multiply 3_1 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputOperation(MULTIPLY)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(1)
        val result = calculatorFeature.calculate().result

        assertEquals("102.3", result)
    }


    @Test
    fun `test direct multiply after answer output True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)

        calculatorFeature.inputOperation(MULTIPLY)

        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        val state = calculatorFeature.inputNumber(6)

        assertEquals("0.9876", state.result)
        val result = calculatorFeature.calculate().result

        assertEquals("1218.6984", result)

        calculatorFeature.inputOperation(PLUS)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(9)

        val result2 = calculatorFeature.calculate().result
        assertNotEquals("0", result2)
        assertEquals("123458007.6984", result2)

        calculatorFeature.inputOperation(DIVIDE)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(7)
        val result3 = calculatorFeature.calculate().result

        assertEquals("345820.7498554622", result3)

        calculatorFeature.inputOperation(MINUS)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(2)
        val result4 = calculatorFeature.calculate().result

        assertEquals("345820.7498", result4)

        calculatorFeature.inputOperation(MULTIPLY)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        val result5 = calculatorFeature.calculate().result

        assertEquals("6916414996", result5)
    }

    @Test
    fun `test percentage dividend True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(9)
        calculatorFeature.inputNumber(8)
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(2)
        val result1 = calculatorFeature.calculate().result

        assertEquals("98765432", result1)
        val result2 = calculatorFeature.calculatePercent().result

        assertEquals("987654.32", result2)

        calculatorFeature.inputOperation(DIVIDE)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        val result3 = calculatorFeature.calculatePercent().result

        assertEquals("20", result3)

        val result4 = calculatorFeature.calculate().result

        assertEquals("49382.716", result4)

        val result5 = calculatorFeature.calculatePercent().result

        assertEquals("493.82716", result5)

        calculatorFeature.inputOperation(MULTIPLY)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(0)
        calculatorFeature.inputNumber(0)
        calculatorFeature.calculatePercent()
        val toggleSign = calculatorFeature.toggleSign()

        assertEquals("-21", toggleSign.result)

        val result6 = calculatorFeature.calculate().result

        assertEquals("-10370.37036", result6)
    }

    @Test
    fun `test percentage multi times True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        calculatorFeature.inputNumber(3)
        calculatorFeature.inputNumber(4)
        calculatorFeature.inputNumber(5)
        calculatorFeature.inputNumber(6)
        calculatorFeature.inputDecimal()
        calculatorFeature.inputNumber(7)
        calculatorFeature.inputNumber(8)
        val inputNumber = calculatorFeature.inputNumber(9)
        assertEquals("123456.789", inputNumber.result)
        val result1 = calculatorFeature.calculate().result
        assertEquals("123456.789", result1)
        val result2 = calculatorFeature.calculatePercent().result
        assertEquals("1234.56789", result2)
        val result3 = calculatorFeature.calculatePercent().result
        assertEquals("12.3456789", result3)
        val result4 = calculatorFeature.calculatePercent().result
        assertEquals("0.123456789", result4)
        val result5 = calculatorFeature.calculate().result
        assertEquals("0.123456789", result5)
    }

    companion object {
        const val MULTIPLY = "*"
        const val DIVIDE = "/"
        const val PLUS = "+"
        const val MINUS = "-"
    }
}