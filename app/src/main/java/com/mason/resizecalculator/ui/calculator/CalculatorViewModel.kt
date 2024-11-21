package com.mason.resizecalculator.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val _displayText1 = MutableLiveData<String>()
    val displayText1: LiveData<String> = _displayText1

    private val _displayFormula1 = MutableLiveData<StringBuilder>()
    val displayFormula1: LiveData<StringBuilder> = _displayFormula1

    private val _displayText2 = MutableLiveData<String>()
    val displayText2: LiveData<String> = _displayText2

    private val _displayFormula2 = MutableLiveData<StringBuilder>()
    val displayFormula2: LiveData<StringBuilder> = _displayFormula2

    // 各自的計算狀態
    private var isNewNumber1 = true
    private var isNewNumber2 = true
    private var formula1 = StringBuilder()
    private var formula2 = StringBuilder()

    init {
        _displayText1.value = "0"
        _displayText2.value = "0"
        _displayFormula1.value = StringBuilder()
        _displayFormula2.value = StringBuilder()
    }

    fun onNumberClick(number: Int, calculatorId: Int) {
        when (calculatorId) {
            1 -> handleNumberInput(number, _displayText1, formula1, _displayFormula1, isNewNumber1)
            2 -> handleNumberInput(number, _displayText2, formula2, _displayFormula2, isNewNumber2)
        }
    }

    private fun handleNumberInput(
        number: Int,
        displayText: MutableLiveData<String>,
        formula: StringBuilder,
        displayFormula: MutableLiveData<StringBuilder>,
        isNewNumber: Boolean
    ) {
        if (isNewNumber) {
            displayText.value = number.toString()
//            isNewNumber = false
        } else {
            displayText.value = "${displayText.value}$number"
        }
        formula.append(number)
        displayFormula.value = formula
    }

    fun onOperationClick(op: String, calculatorId: Int) {
        when (calculatorId) {
            1 -> handleOperation(op, formula1, _displayFormula1, isNewNumber1)
            2 -> handleOperation(op, formula2, _displayFormula2, isNewNumber2)
        }
    }

    private fun handleOperation(
        op: String,
        formula: StringBuilder,
        displayFormula: MutableLiveData<StringBuilder>,
        isNewNumber: Boolean
    ) {
        formula.append(op)
        displayFormula.value = formula
//        isNewNumber = true
    }

    fun onEqualsClick(calculatorId: Int) {
        when (calculatorId) {
            1 -> handleEquals(formula1, _displayText1, _displayFormula1, isNewNumber1)
            2 -> handleEquals(formula2, _displayText2, _displayFormula2, isNewNumber2)
        }
    }

    private fun handleEquals(
        formula: StringBuilder,
        displayText: MutableLiveData<String>,
        displayFormula: MutableLiveData<StringBuilder>,
        isNewNumber: Boolean
    ) {
        val result = calculateFormula(formula)
        displayText.value = result.toString()
        formula.clear()
        displayFormula.value = formula
//        isNewNumber = true
    }

    fun onClearClick(calculatorId: Int) {
        when (calculatorId) {
            1 -> handleClear(_displayText1, formula1, _displayFormula1, isNewNumber1)
            2 -> handleClear(_displayText2, formula2, _displayFormula2, isNewNumber2)
        }
    }

    private fun handleClear(
        displayText: MutableLiveData<String>,
        formula: StringBuilder,
        displayFormula: MutableLiveData<StringBuilder>,
        isNewNumber: Boolean
    ) {
        displayText.value = "0"
        formula.clear()
        displayFormula.value = formula
//        isNewNumber = true
    }

    private fun calculateFormula(formula: StringBuilder): Double {
        if (formula.isEmpty()) return 0.0

        try {
            val numbers = mutableListOf<Double>()
            val operators = mutableListOf<Char>()
            var currentNumber = StringBuilder()

            formula.forEach { char ->
                when {
                    char.isDigit() -> currentNumber.append(char)
                    char in setOf('+', '-', '*', '/') -> {
                        if (currentNumber.isNotEmpty()) {
                            numbers.add(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        operators.add(char)
                    }
                }
            }
            if (currentNumber.isNotEmpty()) {
                numbers.add(currentNumber.toString().toDouble())
            }

            var i = 0
            while (i < operators.size) {
                if (operators[i] in setOf('*', '/')) {
                    val result = when (operators[i]) {
                        '*' -> numbers[i] * numbers[i + 1]
                        '/' -> if (numbers[i + 1] != 0.0) numbers[i] / numbers[i + 1]
                        else throw ArithmeticException("除數不能為零")

                        else -> 0.0
                    }
                    numbers[i] = result
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                    i--
                }
                i++
            }

            var result = numbers[0]
            for (i in operators.indices) {
                result = when (operators[i]) {
                    '+' -> result + numbers[i + 1]
                    '-' -> result - numbers[i + 1]
                    else -> result
                }
            }

            return result

        } catch (e: Exception) {
            return 0.0
        }
    }
} 