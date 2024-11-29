package com.mason.resizecalculator.ui.calculator

import org.junit.Test
import org.junit.Rule
import org.junit.Before
import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule

class CalculatorViewModelTest {
    private lateinit var viewModel: CalculatorViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = CalculatorViewModel()
    }

    @Test
    fun `CalculatorViewModel_Initialized_ShouldShowDefaultValues`() {
        assertEquals("0", viewModel.displayResultLeft.value)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaLeft.value)
        assertEquals("", viewModel.displayFormulaRight.value)
    }

    @Test
    fun `MinusOperation_WhenUserInputMinusInBeginning_ShouldShowCorrectResult`() {
        viewModel.onOperationClick(MINUS, CalculatorId.LEFT)
        assertEquals("-", viewModel.displayFormulaLeft.value)

        viewModel.onNumberClick(9, CalculatorId.LEFT)
        viewModel.onNumberClick(8, CalculatorId.LEFT)
        viewModel.onNumberClick(7, CalculatorId.LEFT)
        viewModel.onNumberClick(6, CalculatorId.LEFT)
        assertEquals("-9876", viewModel.displayFormulaLeft.value)
        assertEquals("-9876", viewModel.displayResultLeft.value)

        viewModel.onOperationClick(MINUS, CalculatorId.RIGHT)
        assertEquals("-", viewModel.displayFormulaRight.value)

        viewModel.onNumberClick(1, CalculatorId.RIGHT)
        viewModel.onNumberClick(2, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        viewModel.onNumberClick(7, CalculatorId.RIGHT)
        assertEquals("-1234567", viewModel.displayFormulaRight.value)
        assertEquals("-1234567", viewModel.displayResultRight.value)
    }

    @Test
    fun `NumberInput_WhenUserInputsMultipleDigits_ShouldUpdateDisplay`() {
        // test left and right together
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        assertEquals("12", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        assertEquals("34", viewModel.displayResultRight.value)

        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        assertEquals("1243", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        assertEquals("3456", viewModel.displayResultRight.value)

        viewModel.onNumberClick(7, CalculatorId.LEFT)
        viewModel.onNumberClick(7, CalculatorId.LEFT)
        assertEquals("124377", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(9, CalculatorId.RIGHT)
        viewModel.onNumberClick(8, CalculatorId.RIGHT)
        assertEquals("345698", viewModel.displayResultRight.value)
    }

    @Test
    fun `BasicCalculation_WhenPerformingAddition_ShouldShowCorrectResult`() {
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(0, CalculatorId.LEFT)
        viewModel.onNumberClick(0, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("5732", viewModel.displayResultLeft.value)
    }

    @Test
    fun `ValueCopy_WhenMovingBetweenCalculators_ShouldCopyCorrectly`() {
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("6", viewModel.displayResultLeft.value)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaRight.value)

        viewModel.onMoveToRightClick()
        assertEquals("6", viewModel.displayResultRight.value)

        viewModel.onPercentClick(CalculatorId.RIGHT)
        viewModel.onOperationClick(MULTIPLY, CalculatorId.RIGHT)
        viewModel.onNumberClick(1, CalculatorId.RIGHT)
        viewModel.onNumberClick(2, CalculatorId.RIGHT)
        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("7.2", viewModel.displayResultRight.value)

        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onMoveToLeftClick()
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("13.2", viewModel.displayResultLeft.value)
        assertEquals("7.2", viewModel.displayResultRight.value)

        viewModel.onSignClick(CalculatorId.LEFT)
        viewModel.onOperationClick(DIVIDE, CalculatorId.RIGHT)
        viewModel.onMoveToRightClick()
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("0.5454545455", viewModel.displayResultRight.value)

        viewModel.onMoveToLeftClick()
        assertEquals("0.5454545455", viewModel.displayResultLeft.value)
    }

    @Test
    fun `SpecialOperations_WhenUsingSign_ShouldCalculateCorrectly`() {
        // left calculator
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onSignClick(CalculatorId.LEFT)
        assertEquals("-5", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(5, CalculatorId.LEFT)
        assertEquals("-55", viewModel.displayResultLeft.value)

        viewModel.onSignClick(CalculatorId.LEFT)
        assertEquals("55", viewModel.displayResultLeft.value)

        // right calculator
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        viewModel.onSignClick(CalculatorId.RIGHT)
        assertEquals("-6", viewModel.displayResultRight.value)

        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        assertEquals("-66", viewModel.displayResultRight.value)

        viewModel.onSignClick(CalculatorId.RIGHT)
        assertEquals("66", viewModel.displayResultRight.value)
    }

    @Test
    fun `SpecialOperations_WhenUsingPercent_ShouldCalculateCorrectly`() {
        // Left calculator
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(4, CalculatorId.LEFT)
        assertEquals("1234", viewModel.displayResultLeft.value)
        assertEquals("1234", viewModel.displayFormulaLeft.value)

        viewModel.onPercentClick(CalculatorId.LEFT)
        assertEquals("12.34", viewModel.displayResultLeft.value)
        assertEquals("12.34", viewModel.displayFormulaLeft.value)

        viewModel.onPercentClick(CalculatorId.LEFT)
        assertEquals("0.1234", viewModel.displayResultLeft.value)
        assertEquals("0.1234", viewModel.displayFormulaLeft.value)

        viewModel.onNumberClick(9, CalculatorId.LEFT)
        viewModel.onNumberClick(8, CalculatorId.LEFT)
        assertEquals("0.123498", viewModel.displayResultLeft.value)
        assertEquals("0.123498", viewModel.displayFormulaLeft.value)

        viewModel.onPercentClick(CalculatorId.LEFT)
        assertEquals("0.00123498", viewModel.displayResultLeft.value)
        assertEquals("0.00123498", viewModel.displayFormulaLeft.value)

        // Right calculator
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onNumberClick(2, CalculatorId.RIGHT)
        assertEquals("5432", viewModel.displayResultRight.value)
        assertEquals("5432", viewModel.displayFormulaRight.value)

        viewModel.onPercentClick(CalculatorId.RIGHT)
        assertEquals("54.32", viewModel.displayResultRight.value)
        assertEquals("54.32", viewModel.displayFormulaRight.value)

        viewModel.onPercentClick(CalculatorId.RIGHT)
        assertEquals("0.5432", viewModel.displayResultRight.value)
        assertEquals("0.5432", viewModel.displayFormulaRight.value)

        viewModel.onNumberClick(7, CalculatorId.RIGHT)
        viewModel.onNumberClick(8, CalculatorId.RIGHT)
        viewModel.onNumberClick(9, CalculatorId.RIGHT)
        assertEquals("0.5432789", viewModel.displayResultRight.value)
        assertEquals("0.5432789", viewModel.displayFormulaRight.value)

        viewModel.onPercentClick(CalculatorId.RIGHT)
        assertEquals("0.005432789", viewModel.displayResultRight.value)
        assertEquals("0.005432789", viewModel.displayFormulaRight.value)
    }

    @Test
    fun `DeleteOperation_WhenDeletingLastDigit_ShouldRemoveCorrectly`() {
        // left calculator
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("12", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        viewModel.onNumberClick(6, CalculatorId.LEFT)
        assertEquals("1246", viewModel.displayResultLeft.value)

        viewModel.onNumberClick(7, CalculatorId.LEFT)
        viewModel.onSignClick(CalculatorId.LEFT)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("-1246", viewModel.displayResultLeft.value)

        viewModel.onPercentClick(CalculatorId.LEFT)
        assertEquals("-12.46", viewModel.displayResultLeft.value)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("-12.4", viewModel.displayResultLeft.value)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("-12.4", viewModel.displayResultLeft.value)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("-12.", viewModel.displayResultLeft.value)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("-12", viewModel.displayResultLeft.value)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("-12", viewModel.displayResultLeft.value)

        // right calculator
        viewModel.onNumberClick(1, CalculatorId.RIGHT)
        viewModel.onNumberClick(2, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        assertEquals("12", viewModel.displayResultRight.value)

        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        assertEquals("1246", viewModel.displayResultRight.value)

        viewModel.onNumberClick(7, CalculatorId.RIGHT)
        viewModel.onSignClick(CalculatorId.RIGHT)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        assertEquals("-1246", viewModel.displayResultRight.value)

        viewModel.onPercentClick(CalculatorId.RIGHT)
        assertEquals("-12.46", viewModel.displayResultRight.value)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        assertEquals("-12.4", viewModel.displayResultRight.value)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("-12.4", viewModel.displayResultRight.value)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        assertEquals("-12.", viewModel.displayResultRight.value)
        viewModel.onDeleteClick(CalculatorId.RIGHT)
        assertEquals("-12", viewModel.displayResultRight.value)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("-12", viewModel.displayResultRight.value)
    }

    @Test
    fun `ClearOperation_WhenClearingInput_ShouldResetToDefault`() {
        // Left calculator
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onClearClick(CalculatorId.LEFT)
        assertEquals("0", viewModel.displayResultLeft.value)
        assertEquals("", viewModel.displayFormulaLeft.value)

        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onNumberClick(6, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("90", viewModel.displayResultLeft.value)

        viewModel.onClearClick(CalculatorId.LEFT)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaRight.value)

        // Right calculator
        viewModel.onNumberClick(9, CalculatorId.RIGHT)
        viewModel.onNumberClick(8, CalculatorId.RIGHT)
        viewModel.onClearClick(CalculatorId.RIGHT)
        viewModel.onClearClick(CalculatorId.RIGHT)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaRight.value)

        viewModel.onNumberClick(7, CalculatorId.RIGHT)
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        viewModel.onOperationClick(MULTIPLY, CalculatorId.RIGHT)
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("4028", viewModel.displayResultRight.value)

        viewModel.onClearClick(CalculatorId.RIGHT)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaRight.value)

        viewModel.onOperationClick(DIVIDE, CalculatorId.RIGHT)
        viewModel.onClearClick(CalculatorId.RIGHT)
        assertEquals("0", viewModel.displayResultRight.value)
        assertEquals("", viewModel.displayFormulaRight.value)
    }

    @Test
    fun `BasicCalculation_WhenEqualsOperation_ShouldShowCorrectDisplayResult`() {
        // left calculator
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(0, CalculatorId.LEFT)
        viewModel.onNumberClick(0, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("5732", viewModel.displayResultLeft.value)
        assertEquals("5432+300=5732", viewModel.displayFormulaLeft.value)

        viewModel.onOperationClick(MINUS, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("3387", viewModel.displayResultLeft.value)
        assertEquals("5732-2345=3387", viewModel.displayFormulaLeft.value)

        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onDecimalClick(CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("23", viewModel.displayResultLeft.value)
        assertEquals("23.=23", viewModel.displayFormulaLeft.value)

        viewModel.onNumberClick(4, CalculatorId.LEFT)
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onNumberClick(6, CalculatorId.LEFT)
        viewModel.onDecimalClick(CalculatorId.LEFT)
        viewModel.onNumberClick(0, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("456", viewModel.displayResultLeft.value)
        assertEquals("456.0=456", viewModel.displayFormulaLeft.value)

        // right calculator
        viewModel.onNumberClick(9, CalculatorId.RIGHT)
        viewModel.onNumberClick(8, CalculatorId.RIGHT)
        viewModel.onNumberClick(7, CalculatorId.RIGHT)
        viewModel.onNumberClick(6, CalculatorId.RIGHT)
        viewModel.onOperationClick(PLUS, CalculatorId.RIGHT)
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("10419", viewModel.displayResultRight.value)
        assertEquals("9876+543=10419", viewModel.displayFormulaRight.value)

        viewModel.onOperationClick(MINUS, CalculatorId.RIGHT)
        viewModel.onNumberClick(1, CalculatorId.RIGHT)
        viewModel.onNumberClick(2, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        assertEquals("12345", viewModel.displayResultRight.value)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("-1926", viewModel.displayResultRight.value)
        assertEquals("10419-12345=-1926", viewModel.displayFormulaRight.value)

        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onNumberClick(9, CalculatorId.RIGHT)
        viewModel.onNumberClick(8, CalculatorId.RIGHT)
        viewModel.onDecimalClick(CalculatorId.RIGHT)
        viewModel.onDecimalClick(CalculatorId.RIGHT)
        viewModel.onDecimalClick(CalculatorId.RIGHT)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("98", viewModel.displayResultRight.value)
        assertEquals("98.=98", viewModel.displayFormulaRight.value)

        viewModel.onNumberClick(5, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onDecimalClick(CalculatorId.RIGHT)
        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onEqualsClick(CalculatorId.RIGHT)
        assertEquals("543", viewModel.displayResultRight.value)
        assertEquals("543.000=543", viewModel.displayFormulaRight.value)
    }

    companion object {
        private const val PLUS = "+"
        private const val MINUS = "-"
        private const val MULTIPLY = "*"
        private const val DIVIDE = "/"

    }
}