package com.mason.resizecalculator.ui.calculator

import org.junit.Test

import org.junit.Assert.*

class CalculatorFeatureTest {
    var calculatorFeature = CalculatorFeature()

    @Test
    fun `test inputNumber with 1 True`() {
        calculatorFeature.clear()
        val state = calculatorFeature.inputNumber(1)
        assertEquals("1", state.result)
        assertNotEquals("1.0", state.result)
        assertNotEquals("0", state.result)
    }

    @Test
    fun `test decimal inputNumber with 123 False`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        val state = calculatorFeature.inputNumber(3)
        assertNotEquals("123.0", state.result)
    }

    @Test
    fun `test inputNumber with 123 True`() {
        calculatorFeature.clear()
        calculatorFeature.inputNumber(1)
        calculatorFeature.inputNumber(2)
        val state = calculatorFeature.inputNumber(3)
        assertEquals("123", state.result)
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
        // TODO
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
        assertEquals("123458007.6984000057", result2)

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

    companion object {
        const val TAG = "CalculatorFragment"
        const val MULTIPLY = "*"
        const val DIVIDE = "/"
        const val PLUS = "+"
        const val MINUS = "-"


    }
}