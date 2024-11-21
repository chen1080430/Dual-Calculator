package com.mason.resizecalculator.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val calculator1 = CalculatorFeature()
    private val calculator2 = CalculatorFeature()
    // Calculator list
//    private val calculatorList = listOf(calculator1, calculator2)

    private val _displayResult1 = MutableLiveData<String>()
    val displayResult1: LiveData<String> = _displayResult1

    private val _displayFormula1 = MutableLiveData<String>()
    val displayFormula1: LiveData<String> = _displayFormula1

    private val _displayResult2 = MutableLiveData<String>()
    val displayResult2: LiveData<String> = _displayResult2

    private val _displayFormula2 = MutableLiveData<String>()
    val displayFormula2: LiveData<String> = _displayFormula2

    init {
        _displayResult1.value = "0"
        _displayResult2.value = "0"
        _displayFormula1.value = ""
        _displayFormula2.value = ""
    }

    fun onNumberClick(number: Int, calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.inputNumber(number)
            2 -> calculator2.inputNumber(number)
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onOperationClick(op: String, calculatorId: Int) {
        val receivedAnswer = when (calculatorId) {
            1 -> calculator1.getAnswer()
            2 -> calculator2.getAnswer()
            else -> return
        }

        val state = when (calculatorId) {
            1 -> calculator1.inputOperation(op, if (receivedAnswer != 0.0) receivedAnswer else null)
            2 -> calculator2.inputOperation(op, if (receivedAnswer != 0.0) receivedAnswer else null)
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onDecimalClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.inputDecimal()
            2 -> calculator2.inputDecimal()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onSignClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.toggleSign()
            2 -> calculator2.toggleSign()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onPercentClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.calculatePercent()
            2 -> calculator2.calculatePercent()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onDeleteClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.delete()
            2 -> calculator2.delete()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onEqualsClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.calculate()
            2 -> calculator2.calculate()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    fun onClearClick(calculatorId: Int) {
        val state = when (calculatorId) {
            1 -> calculator1.clear()
            2 -> calculator2.clear()
            else -> return
        }
        updateDisplay(calculatorId, state)
    }

    private fun updateDisplay(calculatorId: Int, state: CalculatorState) {
        when (calculatorId) {
            1 -> {
                _displayResult1.value = state.result
                _displayFormula1.value = state.formula
            }

            2 -> {
                _displayResult2.value = state.result
                _displayFormula2.value = state.formula
            }
        }
    }
}