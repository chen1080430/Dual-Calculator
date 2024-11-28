package com.mason.resizecalculator.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val leftCalculator = CalculatorFunction()
    private val rightCalculator = CalculatorFunction()

    private val _displayResultLeft = MutableLiveData<String>()
    val displayResultLeft: LiveData<String> = _displayResultLeft

    private val _displayFormulaLeft = MutableLiveData<String>()
    val displayFormulaLeft: LiveData<String> = _displayFormulaLeft

    private val _displayResultRight = MutableLiveData<String>()
    val displayResultRight: LiveData<String> = _displayResultRight

    private val _displayFormulaRight = MutableLiveData<String>()
    val displayFormulaRight: LiveData<String> = _displayFormulaRight

    init {
        _displayResultLeft.value = "0"
        _displayResultRight.value = "0"
        _displayFormulaLeft.value = ""
        _displayFormulaRight.value = ""
    }

    fun onNumberClick(number: Int, calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.inputNumber(number)
            CalculatorId.RIGHT -> rightCalculator.inputNumber(number)
        }
        updateDisplay(calculatorId, state)
    }

    fun onOperationClick(op: String, calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.inputOperation(op)
            CalculatorId.RIGHT -> rightCalculator.inputOperation(op)
        }
        updateDisplay(calculatorId, state)
    }

    fun onDecimalClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.inputDecimal()
            CalculatorId.RIGHT -> rightCalculator.inputDecimal()
        }
        updateDisplay(calculatorId, state)
    }

    fun onSignClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.toggleSign()
            CalculatorId.RIGHT -> rightCalculator.toggleSign()
        }
        updateDisplay(calculatorId, state)
    }

    fun onPercentClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.calculatePercent()
            CalculatorId.RIGHT -> rightCalculator.calculatePercent()
        }
        updateDisplay(calculatorId, state)
    }

    fun onDeleteClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.delete()
            CalculatorId.RIGHT -> rightCalculator.delete()
        }
        updateDisplay(calculatorId, state)
    }

    fun onEqualsClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.calculate()
            CalculatorId.RIGHT -> rightCalculator.calculate()
        }
        updateDisplay(calculatorId, state)
    }

    fun onClearClick(calculatorId: CalculatorId) {
        val state = when (calculatorId) {
            CalculatorId.LEFT -> leftCalculator.clear()
            CalculatorId.RIGHT -> rightCalculator.clear()
        }
        updateDisplay(calculatorId, state)
    }

    private fun updateDisplay(calculatorId: CalculatorId, state: CalculatorState) {
        when (calculatorId) {
            CalculatorId.LEFT -> {
                _displayResultLeft.value = state.result
                _displayFormulaLeft.value = state.formula
            }

            CalculatorId.RIGHT -> {
                _displayResultRight.value = state.result
                _displayFormulaRight.value = state.formula
            }
        }
    }

    fun onMoveToLeftClick() {
        updateDisplay(
            CalculatorId.LEFT,
            leftCalculator.inputCompleteNumber(rightCalculator.getAnswer())
        )

    }

    fun onMoveToRightClick() {
        rightCalculator.inputCompleteNumber(leftCalculator.getAnswer())
        updateDisplay(
            CalculatorId.RIGHT,
            rightCalculator.inputCompleteNumber(leftCalculator.getAnswer())
        )
    }
}

enum class CalculatorId {
    LEFT, RIGHT
}