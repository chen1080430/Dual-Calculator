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
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        assertEquals("12", viewModel.displayResultLeft.value)
        
        viewModel.onNumberClick(3, CalculatorId.RIGHT)
        viewModel.onNumberClick(4, CalculatorId.RIGHT)
        assertEquals("34", viewModel.displayResultRight.value)
    }
    
    @Test
    fun `BasicCalculation_WhenPerformingAddition_ShouldShowCorrectResult`() {
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onOperationClick(PLUS, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onEqualsClick(CalculatorId.LEFT)
        assertEquals("8", viewModel.displayResultLeft.value)
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
        viewModel.onNumberClick(5, CalculatorId.LEFT)
        viewModel.onSignClick(CalculatorId.LEFT)
        assertEquals("-5", viewModel.displayResultLeft.value)
        
        viewModel.onNumberClick(1, CalculatorId.RIGHT)
        viewModel.onNumberClick(0, CalculatorId.RIGHT)
        viewModel.onPercentClick(CalculatorId.RIGHT)
        assertEquals("0.1", viewModel.displayResultRight.value)
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
        viewModel.onNumberClick(1, CalculatorId.LEFT)
        viewModel.onNumberClick(2, CalculatorId.LEFT)
        viewModel.onNumberClick(3, CalculatorId.LEFT)
        viewModel.onDeleteClick(CalculatorId.LEFT)
        assertEquals("12", viewModel.displayResultLeft.value)
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

    companion object {
        private const val PLUS = "+"
        private const val MINUS = "-"
        private const val MULTIPLY = "*"
        private const val DIVIDE = "/"

    }
}