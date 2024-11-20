package com.mason.resizecalculator.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    private val _displayText = MutableLiveData<String>()
    val displayText: LiveData<String> = _displayText
    
    private var firstNumber = 0.0
    private var operation: String? = null
    private var isNewNumber = true
    private var formula : String? = null
    
    fun onNumberClick(number: Int, displayScreen: Int = 1) {
        if (isNewNumber) {
            _displayText.value = number.toString()
            isNewNumber = false
        } else {
            _displayText.value = "${_displayText.value}$number"
        }
    }
    
    fun onOperationClick(op: String, displayScreen: Int = 1) {
        firstNumber = _displayText.value?.toDoubleOrNull() ?: 0.0
        operation = op
        isNewNumber = true
    }
    
    fun onEqualsClick(displayScreen: Int = 1) {
        val secondNumber = _displayText.value?.toDoubleOrNull() ?: 0.0
        val result = when(operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
            else -> secondNumber
        }
        _displayText.value = result.toString()
        isNewNumber = true
    }
    
    fun onClearClick(displayScreen: Int = 1) {
        _displayText.value = "0"
        firstNumber = 0.0
        operation = null
        isNewNumber = true
    }
} 