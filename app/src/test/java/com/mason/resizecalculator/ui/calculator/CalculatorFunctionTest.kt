package com.mason.resizecalculator.ui.calculator

import org.junit.Test

import org.junit.Assert.*

class CalculatorFunctionTest {
    private var calculatorFunction = CalculatorFunction()

    @Test
    fun `SingleDigit_WhenInputZeroThenOne_ShouldDisplayOne`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(0)
        val state = calculatorFunction.inputNumber(1)

        assertEquals("1", state.result)
        assertNotEquals("1.0", state.result)
        assertNotEquals("0", state.result)

        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(0)
        val result = calculatorFunction.calculate().result
        assertEquals("1", result)
    }

    @Test
    fun `MultipleDigits_WhenInputThreeNumbers_ShouldDisplayCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        val state = calculatorFunction.inputNumber(3)

        assertEquals("123", state.result)
        assertNotEquals("123.0", state.result)
    }

    @Test
    fun `Addition_WhenUsingLargeNumbersWithSign_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        val input = calculatorFunction.inputNumber(5).result

        assertEquals("12345", input)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        val result1 = calculatorFunction.calculate().result

        assertEquals("112344", result1)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        val result2 = calculatorFunction.calculate().result

        assertEquals("1235789", result2)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(9)
        calculatorFunction.toggleSign()
        calculatorFunction.inputNumber(9)
        val result3 = calculatorFunction.calculate().result

        assertEquals("-10", result3)
    }

    @Test
    fun `Addition_WhenUsingNineDigitNumbers_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(8)
        val input = calculatorFunction.inputNumber(9).result

        assertEquals("123456789", input)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(1)
        val result1 = calculatorFunction.calculate().result
        assertEquals("1111111110", result1)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        val result2 = calculatorFunction.calculate().result
        assertEquals("1111112097", result2)
    }

    @Test
    fun `Subtraction_WhenUsingNegativeNumbers_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        val input = calculatorFunction.inputNumber(0).result

        assertEquals("-10000", input)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        val result1 = calculatorFunction.calculate().result

        assertEquals("-109999", result1)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        val result2 = calculatorFunction.calculate().result

        assertNotEquals("-100000", result2)
        assertEquals("-119998", result2)
    }

    @Test
    fun `Subtraction_WhenUsingSignToggle_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        val input = calculatorFunction.inputNumber(5).result

        assertEquals("12345", input)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        val result1 = calculatorFunction.calculate().result

        assertEquals("-87654", result1)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        val result2 = calculatorFunction.calculate().result

        assertEquals("-1211099", result2)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(9)
        calculatorFunction.toggleSign()
        calculatorFunction.inputNumber(9)
        val result3 = calculatorFunction.calculate().result

        assertEquals("24700", result3)
    }

    @Test
    fun `Multiplication_WhenUsingDecimalNumbers_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(9)

        calculatorFunction.inputOperation(MULTIPLY)

        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(2)
        val state = calculatorFunction.inputNumber(1)
        assertEquals("0.987654321", state.result)

        val result = calculatorFunction.calculate().result
        assertEquals("12193.2631112635", result)
    }

    @Test
    fun `Multiplication_WhenStartingWithZero_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(9)
        val state = calculatorFunction.inputNumber(9)
        assertEquals("9999", state.result)
        calculatorFunction.inputOperation(MULTIPLY)
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(9)
        val result = calculatorFunction.calculate().result
        assertEquals("98990.1", result)
    }

    @Test
    fun `Multiplication_WhenUsingSimpleNumbers_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputOperation(MULTIPLY)
        calculatorFunction.inputNumber(3)
        val result = calculatorFunction.calculate().result

        assertEquals("369", result)
    }

    @Test
    fun `Division_WhenDividingByThree_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputOperation(DIVIDE)
        calculatorFunction.inputNumber(3)
        val result = calculatorFunction.calculate().result

        assertEquals("329", result)
    }

    @Test
    fun `Division_WhenResultHasDecimal_ShouldDisplayCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputOperation(DIVIDE)
        calculatorFunction.inputNumber(2)
        val result = calculatorFunction.calculate().result

        assertNotEquals("493.50", result)
        assertNotEquals("493", result)
        assertEquals("493.5", result)
    }

    @Test
    fun `Multiplication_WhenUsingDecimalOperand_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputOperation(MULTIPLY)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(1)
        val result = calculatorFunction.calculate().result

        assertEquals("102.3", result)
    }

    @Test
    fun `ChainedOperations_WhenUsingMultipleOperators_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)

        calculatorFunction.inputOperation(MULTIPLY)

        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        val state = calculatorFunction.inputNumber(6)

        assertEquals("0.9876", state.result)
        val result = calculatorFunction.calculate().result

        assertEquals("1218.6984", result)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(9)

        val result2 = calculatorFunction.calculate().result
        assertNotEquals("0", result2)
        assertEquals("123458007.6984", result2)

        calculatorFunction.inputOperation(DIVIDE)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(7)
        val result3 = calculatorFunction.calculate().result

        assertEquals("345820.7498554622", result3)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(2)
        val result4 = calculatorFunction.calculate().result

        assertEquals("345820.7498", result4)

        calculatorFunction.inputOperation(MULTIPLY)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        val result5 = calculatorFunction.calculate().result

        assertEquals("6916414996", result5)
    }

    @Test
    fun `Percentage_WhenUsingWithDivision_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(9)
        calculatorFunction.inputNumber(8)
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(2)
        val result1 = calculatorFunction.calculate().result

        assertEquals("98765432", result1)
        val result2 = calculatorFunction.calculatePercent().result

        assertEquals("987654.32", result2)

        calculatorFunction.inputOperation(DIVIDE)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        val result3 = calculatorFunction.calculatePercent().result

        assertEquals("20", result3)

        val result4 = calculatorFunction.calculate().result

        assertEquals("49382.716", result4)

        val result5 = calculatorFunction.calculatePercent().result

        assertEquals("493.82716", result5)

        calculatorFunction.inputOperation(MULTIPLY)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(0)
        calculatorFunction.inputNumber(0)
        calculatorFunction.calculatePercent()
        val toggleSign = calculatorFunction.toggleSign()

        assertEquals("-21", toggleSign.result)

        val result6 = calculatorFunction.calculate().result

        assertEquals("-10370.37036", result6)
    }

    @Test
    fun `Percentage_WhenAppliedMultipleTimes_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        calculatorFunction.inputNumber(3)
        calculatorFunction.inputNumber(4)
        calculatorFunction.inputNumber(5)
        calculatorFunction.inputNumber(6)
        calculatorFunction.inputDecimal()
        calculatorFunction.inputNumber(7)
        calculatorFunction.inputNumber(8)
        val inputNumber = calculatorFunction.inputNumber(9)
        assertEquals("123456.789", inputNumber.result)
        val result1 = calculatorFunction.calculate().result
        assertEquals("123456.789", result1)
        val result2 = calculatorFunction.calculatePercent().result
        assertEquals("1234.56789", result2)
        val result3 = calculatorFunction.calculatePercent().result
        assertEquals("12.3456789", result3)
        val result4 = calculatorFunction.calculatePercent().result
        assertEquals("0.123456789", result4)
        val result5 = calculatorFunction.calculate().result
        assertEquals("0.123456789", result5)
    }

    @Test
    fun `CompleteNumber_WhenAddCompleteNumber_ShouldCalculateCorrectly`() {
        calculatorFunction.clear()
        calculatorFunction.inputNumber(1)
        calculatorFunction.inputNumber(2)
        val inputNumber = calculatorFunction.inputCompleteNumber(12345.0)
        assertEquals("12345", inputNumber.result)

        calculatorFunction.inputOperation(PLUS)
        calculatorFunction.inputCompleteNumber(9999.0)
        val result1 = calculatorFunction.calculate().result
        assertEquals("22344", result1)

        calculatorFunction.inputOperation(MINUS)
        calculatorFunction.inputCompleteNumber(19.9905)
        val result2 = calculatorFunction.calculate().result
        assertEquals("22324.0095", result2)

        calculatorFunction.inputCompleteNumber(98765.543)
        assertEquals("98765.543", calculatorFunction.calculate().result)

        calculatorFunction.inputCompleteNumber(9876543.0)
        calculatorFunction.toggleSign()
        assertEquals("-9876543", calculatorFunction.calculate().result)

        calculatorFunction.toggleSign()
        calculatorFunction.toggleSign()
        calculatorFunction.calculatePercent()
        calculatorFunction.calculatePercent()
        assertEquals("-987.6543", calculatorFunction.calculate().result)

        calculatorFunction.clear()
        calculatorFunction.inputCompleteNumber(11223344.0)
        calculatorFunction.delete()
        assertEquals("1122334", calculatorFunction.calculate().result)
    }

    companion object {
        const val MULTIPLY = "*"
        const val DIVIDE = "/"
        const val PLUS = "+"
        const val MINUS = "-"
    }
}