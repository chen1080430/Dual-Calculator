package com.mason.resizecalculator.ui.calculator

import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorFeature {
    private var formula = StringBuilder()
    private var currentNumber = StringBuilder()
    private var answer = BigDecimal.ZERO
    private var isNewNumber = true
    private val state = CalculatorState()

    fun inputNumber(number: Int): CalculatorState {
        if (isNewNumber) {
            currentNumber.clear()
            isNewNumber = false
        }
        currentNumber.append(number)

        if (currentNumber.startsWith("0")
            && currentNumber.length > 1
            && currentNumber[1] != '.'
        ) {
            currentNumber.deleteCharAt(0)
        }

        state.updateState(
            newResult = currentNumber.toString(),
            newFormula = buildDisplayFormula()
        )
        return state
    }

    fun inputCompleteNumber(number: Double): CalculatorState {
        currentNumber.clear()
        currentNumber.append(removeDot(number))
        if (isNewNumber) {
            isNewNumber = false
            answer = BigDecimal(number)
        }

        state.updateState(
            newResult = currentNumber.toString(),
            newFormula = buildDisplayFormula()
        )
        return state
    }

    fun inputDecimal(): CalculatorState {
        if (isNewNumber) {
            currentNumber.clear()
            currentNumber.append("0")
            isNewNumber = false
        }

        if (!currentNumber.contains(".")) {
            currentNumber.append(".")
        }

        state.updateState(
            newResult = currentNumber.toString(),
            newFormula = buildDisplayFormula()
        )
        return state
    }

    fun toggleSign(): CalculatorState {
        if (currentNumber.isEmpty()) {
            if (answer != BigDecimal.ZERO) {
                currentNumber.append(answer)
            } else {
                return state
            }
        }

        if (currentNumber[0] == '-') {
            currentNumber.deleteCharAt(0)
        } else {
            currentNumber.insert(0, '-')
        }

        state.updateState(
            newResult = currentNumber.toString(),
            newFormula = buildDisplayFormula()
        )
        return state
    }

    fun calculatePercent(): CalculatorState {
        if (currentNumber.isEmpty()) {
            if (answer != BigDecimal.ZERO) {
                currentNumber.append(answer)
            } else {
                return state
            }
        }

        try {
            val number = BigDecimal(currentNumber.toString())
            val hundred = BigDecimal("100")
            val result = number.divide(hundred, 10, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
            currentNumber.clear()
            currentNumber.append(result)

            state.updateState(
                newResult = currentNumber.toString(),
                newFormula = buildDisplayFormula()
            )
        } catch (e: NumberFormatException) {
            clear()
        }
        return state
    }

    fun delete(): CalculatorState {
        when {
            currentNumber.isNotEmpty() -> {
                currentNumber.deleteCharAt(currentNumber.length - 1)
                if (currentNumber.isEmpty()) {
                    currentNumber.append("0")
                    isNewNumber = true
                }
            }

            formula.isNotEmpty() -> {
                val lastChar = formula.last()
                formula.deleteCharAt(formula.length - 1)

                if (lastChar in setOf('+', '-', '*', '/')) {
                    val lastNumberIndex =
                        formula.indexOfLast { it in setOf('+', '-', '*', '/') } + 1
                    if (lastNumberIndex > 0) {
                        currentNumber.append(formula.substring(lastNumberIndex))
                        formula.delete(lastNumberIndex, formula.length)
                        isNewNumber = false
                    } else {
                        currentNumber.append(formula)
                        formula.clear()
                        isNewNumber = false
                    }
                }
            }
        }

        state.updateState(
            newResult = if (currentNumber.isEmpty()) "0" else currentNumber.toString(),
            newFormula = buildDisplayFormula()
        )
        return state
    }

    fun inputOperation(op: String): CalculatorState {
        if (formula.isEmpty() && currentNumber.isEmpty() && op == "-") {
            currentNumber.append(op)
            isNewNumber = false
            state.updateState(
                newResult = currentNumber.toString(),
                newFormula = currentNumber.toString()
            )
            return state
        }

        if (currentNumber.isEmpty() && formula.isEmpty() && answer == BigDecimal.ZERO) {
            toggleSign()
        } else if (currentNumber.isNotEmpty()) {
            formula.append(currentNumber)
        } else if (formula.isEmpty() && answer != BigDecimal.ZERO) {
            formula.append(answer)
        } else if (formula.isNotEmpty() && formula.last() in setOf('+', '-', '*', '/')) {
            formula.deleteCharAt(formula.length - 1)
        }

        formula.append(op)
        currentNumber.clear()
        isNewNumber = true

        state.updateState(
            newResult = if (answer != BigDecimal.ZERO) formatNumber(answer) else "0",
            newFormula = formula.toString()
        )
        return state
    }

    fun calculate(): CalculatorState {
        if (formula.isEmpty() && currentNumber.isEmpty()) return state

        try {
            val fullFormula = StringBuilder(formula)
            fullFormula.append(currentNumber.ifEmpty { "0" })

            answer = calculateFormula(fullFormula)
            val formatAnswer = formatNumber(answer)
            fullFormula.append("=")
            fullFormula.append(formatAnswer)
            formula.clear()
            currentNumber.clear()
            currentNumber.append(formatAnswer)
            isNewNumber = true

            state.updateState(
                newResult = formatAnswer,
                newFormula = fullFormula.toString()
            )
        } catch (e: Exception) {
            clear()
        }
        return state
    }

    fun clear(): CalculatorState {
        formula.clear()
        currentNumber.clear()
        answer = BigDecimal.ZERO
        isNewNumber = true
        state.clear()
        return state
    }

    fun getAnswer(): Double = answer.toDouble()

    private fun buildDisplayFormula(): String {
        return when {
            formula.isEmpty() && currentNumber.isEmpty() -> ""
            formula.isEmpty() -> currentNumber.toString()
            currentNumber.isEmpty() -> formula.toString()
            else -> formula.toString() + currentNumber.toString()
        }
    }

    private fun formatNumber(number: BigDecimal): String {
        return number.setScale(10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
    }

    private fun calculateFormula(formula: StringBuilder): BigDecimal {
        try {
            val numbers = mutableListOf<BigDecimal>()
            val operators = mutableListOf<Char>()
            var currentNumber = StringBuilder()

            if (formula.isNotEmpty() && formula[0] == '-') {
                currentNumber.append('-')
                formula.deleteCharAt(0)
            }

            formula.forEachIndexed { index, char ->
                when {
                    char.isDigit() || char == '.' -> {
                        currentNumber.append(char)
                    }
                    char in setOf('+', '-', '*', '/') -> {
                        if (currentNumber.isNotEmpty()) {
                            numbers.add(BigDecimal(currentNumber.toString()))
                            currentNumber.clear()
                        }
                        if (char == '-' && (index == 0 || formula[index - 1] in setOf('+', '-', '*', '/'))) {
                            currentNumber.append('-')
                        } else {
                            operators.add(char)
                        }
                    }
                }
            }

            if (currentNumber.isNotEmpty()) {
                numbers.add(BigDecimal(currentNumber.toString()))
            }

            if (numbers.isEmpty()) {
                return BigDecimal.ZERO
            }

            var i = 0
            while (i < operators.size) {
                if (operators[i] in setOf('*', '/')) {
                    val result = when (operators[i]) {
                        '*' -> numbers[i].multiply(numbers[i + 1])
                        '/' -> {
                            if (numbers[i + 1].compareTo(BigDecimal.ZERO) == 0) {
                                throw ArithmeticException("除數不能為零")
                            }
                            numbers[i].divide(numbers[i + 1], 10, RoundingMode.HALF_UP)
                        }
                        else -> throw IllegalStateException("未知運算符")
                    }

                    numbers[i] = result
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                } else {
                    i++
                }
            }

            var result = numbers[0]
            for (i in operators.indices) {
                result = when (operators[i]) {
                    '+' -> result.add(numbers[i + 1])
                    '-' -> result.subtract(numbers[i + 1])
                    else -> throw IllegalStateException("未知運算符")
                }
            }

            return result.stripTrailingZeros()

        } catch (e: Exception) {
            when (e) {
                is NumberFormatException -> throw IllegalArgumentException("無效的數字格式")
                is ArithmeticException -> throw e
                else -> throw IllegalStateException("計算錯誤: ${e.message}")
            }
        }
    }

    private fun removeDot(number: Double): String {
        return if (number % 1.0 == 0.0) {
            number.toLong().toString()
        } else {
            number.toString()
        }
    }
}

data class CalculatorState(
    var result: String = "0",
    var formula: String = "",
) {
    fun updateState(newResult: String, newFormula: String) {
        result = newResult
        formula = newFormula
    }

    fun clear() {
        result = "0"
        formula = ""
    }
}