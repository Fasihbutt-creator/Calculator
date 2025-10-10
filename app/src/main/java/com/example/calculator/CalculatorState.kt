package com.example.calculator

data class CalculatorState(
    // expression tokens like ["12", "+", "3", "×", "5"]
    val tokens: List<String> = emptyList(),
    // currently typing number (may be "", "-", "3.14", etc.)
    val currentInput: String = "",
    // result after pressing '=', empty otherwise
    val result: String = ""
)