package com.mason.resizecalculator.ui.calculator

class CalculatorFeature {
    private var formula = StringBuilder()
    private var currentNumber = StringBuilder()
    private var answer = 0.0
    private var isNewNumber = true
    private val state = CalculatorState()

    fun inputNumber(number: Int): CalculatorState {
        if (isNewNumber) {
            currentNumber.clear()
            isNewNumber = false
        }
        currentNumber.append(number)

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
            answer = number
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
            if (answer != 0.0) {
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
            if (answer != 0.0) {
                currentNumber.append(answer)
            } else {
                return state
            }
        }

        try {
            val number = currentNumber.toString().toDouble()
            val result = number / 100.0
            currentNumber.clear()
            currentNumber.append(formatNumber(result))

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
        if (currentNumber.isNotEmpty()) {
            formula.append(currentNumber)
        } else if (formula.last() in setOf('+', '-', '*', '/')) {
            formula.deleteCharAt(formula.length - 1)
        } else if (formula.isEmpty() && answer != 0.0) {
            formula.append(formatNumber(answer))
        }

        formula.append(op)
        currentNumber.clear()
        isNewNumber = true

        state.updateState(
            newResult = if (answer != 0.0) formatNumber(answer) else "0",
            newFormula = formula.toString()
        )
        return state
    }

    fun calculate(): CalculatorState {
        if (formula.isEmpty() && currentNumber.isEmpty()) return state

        try {
            val fullFormula = StringBuilder(formula)
            fullFormula.append(currentNumber.ifEmpty { 0 })

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
        answer = 0.0
        isNewNumber = true
        state.clear()
        return state
    }

    fun getAnswer(): Double = answer

    private fun buildDisplayFormula(): String {
        return when {
            formula.isEmpty() && currentNumber.isEmpty() -> ""
            formula.isEmpty() -> currentNumber.toString()
            currentNumber.isEmpty() -> formula.toString()
            else -> formula.toString() + currentNumber.toString()
        }
    }

    private fun formatNumber(number: Double): String {
//        return if (number == number.toLong().toDouble()) {
//            number.toLong().toString()
//        } else {
//            number.toString()
//        }
        return when {
//            number == number.toLong().toDouble() -> {
//                number.toLong().toString()
//            }
            number % 1.0 == 0.0 -> {
                number.toLong().toString()
            }

            else -> {
                java.math.BigDecimal(number)
                    .setScale(4, java.math.RoundingMode.HALF_UP)
                    .stripTrailingZeros()
                    .toPlainString()
            }
        }
    }

    private fun calculateFormula(formula: StringBuilder): Double {
        try {
            val numbers = mutableListOf<Double>()
            val operators = mutableListOf<Char>()
            var currentNumber = StringBuilder()

            formula.forEachIndexed { index, char ->
                when {
                    char.isDigit() || char == '.' ||
                            (char == '-' && (index == 0 || formula[index - 1] in setOf(
                                '+',
                                '-',
                                '*',
                                '/'
                            ))) -> {
                        currentNumber.append(char)
                    }
                    char in setOf('+', '-', '*', '/') -> {
                        if (currentNumber.isNotEmpty()) {
                            numbers.add(currentNumber.toString().toDouble())
                            currentNumber.clear()
                        }
                        if (char != '-' || (index > 0 && formula[index - 1] !in setOf(
                                '+',
                                '-',
                                '*',
                                '/'
                            ))
                        ) {
                            operators.add(char)
                        }
                    }
                }
            }

            if (currentNumber.isEmpty()) {
                currentNumber = StringBuilder("0")
                this.currentNumber = currentNumber
            }
            numbers.add(currentNumber.toString().toDouble())

            var i = 0
            while (i < operators.size) {
                if (operators[i] in setOf('*', '/')) {
                    val result = when (operators[i]) {
                        '*' -> numbers[i] * numbers[i + 1]
                        '/' -> {
                            if (numbers[i + 1] == 0.0) throw ArithmeticException("除數不能為零")
                            numbers[i] / numbers[i + 1]
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
                    '+' -> result + numbers[i + 1]
                    '-' -> result - numbers[i + 1]
                    else -> throw IllegalStateException("未知運算符")
                }
            }

            return result

        } catch (e: Exception) {
            when (e) {
                is NumberFormatException -> throw IllegalArgumentException("無效的數字格式")
                is ArithmeticException -> throw e
                else -> throw IllegalStateException("計算錯誤")
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
    var currentNumber: Double = 0.0
) {
    fun updateState(newResult: String, newFormula: String) {
        result = newResult
        formula = newFormula
    }

    fun clear() {
        result = "0"
        formula = ""
        currentNumber = 0.0
    }
}