package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayDeque

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorActions) {
        when (action) {
            is CalculatorActions.Number -> enterNumber(action.number)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> clear()
            is CalculatorActions.Delete -> delete()
            is CalculatorActions.Operation -> enterOperator(action.operation)
            is CalculatorActions.Calculate -> calculate()
        }
    }

    private fun enterNumber(number: Int) {
        // If we have a result visible (after '='), starting a number should reset expression
        if (state.result.isNotEmpty() && state.tokens.isEmpty() && state.currentInput.isEmpty()) {
            state = CalculatorState(currentInput = number.toString())
            return
        }

        // append digit to currentInput (avoid leading "00")
        val cur = state.currentInput
        if (cur == "0") {
            state = state.copy(currentInput = number.toString(), result = "")
        } else {
            state = state.copy(currentInput = cur + number.toString(), result = "")
        }
    }

    private fun enterDecimal() {
        val cur = state.currentInput
        if (cur.isEmpty()) {
            state = state.copy(currentInput = "0.", result = "")
        } else if (!cur.contains('.')) {
            state = state.copy(currentInput = cur + ".", result = "")
        }
    }

    private fun clear() {
        state = CalculatorState()
    }

    private fun delete() {
        // If current input non-empty, delete last char
        val cur = state.currentInput
        if (cur.isNotEmpty()) {
            val trimmed = cur.dropLast(1)
            state = state.copy(currentInput = trimmed)
            return
        }

        // If currentInput empty but tokens exist -> remove last token
        val t = state.tokens.toMutableList()
        if (t.isNotEmpty()) {
            t.removeAt(t.lastIndex)
            state = state.copy(tokens = t, result = "")
        } else {
            // Nothing to delete
            state = state.copy(result = "")
        }
    }

    private fun enterOperator(operation: CalculatorOperation) {
        // Reset result if user starts building new expression after result
        if (state.result.isNotEmpty() && state.tokens.isEmpty() && state.currentInput.isEmpty()) {
            // user saw result and pressed operator -> treat result as first operand
            state = state.copy(tokens = listOf(state.result), result = "")
        }

        val opSymbol = operation.symbol

        // If we have currentInput -> push it and then push operator
        if (state.currentInput.isNotEmpty()) {
            val newTokens = state.tokens.toMutableList()
            newTokens.add(state.currentInput)
            newTokens.add(opSymbol)
            state = state.copy(tokens = newTokens, currentInput = "", result = "")
            return
        }

        // If currentInput empty and no tokens -> allow starting negative number on '-' operator
        if (state.currentInput.isEmpty() && state.tokens.isEmpty()) {
            if (operation is CalculatorOperation.Subtract) {
                // start a negative number
                state = state.copy(currentInput = "-", result = "")
            }
            return
        }

        // If last token is an operator and user presses an operator, replace it
        if (state.tokens.isNotEmpty()) {
            val last = state.tokens.last()
            if (isOperator(last)) {
                val t = state.tokens.toMutableList()
                t[t.lastIndex] = opSymbol
                state = state.copy(tokens = t, result = "")
                return
            } else {
                // last token is a number (shouldn't happen often if currentInput empty), append operator
                val t = state.tokens.toMutableList()
                t.add(opSymbol)
                state = state.copy(tokens = t, result = "")
                return
            }
        }
    }
//
//    private fun calculate() {
//        // Build final token list: tokens + currentInput (if any)
//        val finalTokens = state.tokens.toMutableList()
//        if (state.currentInput.isNotEmpty()) {
//            finalTokens.add(state.currentInput)
//        }
//
//        if (finalTokens.isEmpty()) return
//
//        // Evaluate using shunting-yard -> RPN eval with BigDecimal
//        val result = evaluateExpression(finalTokens)
//        if (result != null) {
//            // show result in state.result and clear tokens/currentInput (so next entry starts fresh)
//            state = CalculatorState(tokens = emptyList(), currentInput = "", result = result)
//        } else {
//            // on error, clear state (you can change to show an error token instead)
//            state = CalculatorState()
//        }
//    }


    private fun calculate() {
        // Build final token list: tokens + currentInput (if any)
        val finalTokens = state.tokens.toMutableList()
        if (state.currentInput.isNotEmpty()) {
            finalTokens.add(state.currentInput)
        }

        // nothing to calculate
        if (finalTokens.isEmpty()) return

        // If the last token is an operator (e.g. tokens = ["12", "+"]), do nothing
        // so the user can continue typing; don't clear or change the text field.
        val lastToken = finalTokens.last()
        if (isOperator(lastToken)) {
            return
        }

        // Evaluate using shunting-yard -> RPN eval with BigDecimal
        val result = evaluateExpression(finalTokens)
        if (result != null) {
            // show result in state.result and clear tokens/currentInput (so next entry starts fresh)
            state = CalculatorState(tokens = emptyList(), currentInput = "", result = result)
        } else {
            // on error, clear state (you can change to show an error token instead)
            state = CalculatorState()
        }
    }


    // Helpers

    private fun isOperator(s: String) = s == "+" || s == "-" || s == "x" || s == "/" || s == "*"

    private fun precedence(op: String): Int {
        return when (op) {
            "+", "-" -> 1
            "x", "*" , "/" -> 2
            else -> 0
        }
    }

    private fun normalizeOperatorToken(op: String): String {
        // support both 'x' and '*' for multiply internally
        return when (op) {
            "x" -> "*"
            else -> op
        }
    }

    private fun evaluateExpression(tokens: List<String>): String? {
        return try {
            // Convert infix tokens to RPN (shunting-yard)
            val output = mutableListOf<String>()
            val ops = ArrayDeque<String>()

            for (t in tokens) {
                val token = t.trim()
                if (token.isEmpty()) continue
                if (isOperator(token)) {
                    val op1 = normalizeOperatorToken(token)
                    while (ops.isNotEmpty() && isOperator(ops.peek()) &&
                        (precedence(normalizeOperatorToken(ops.peek())) >= precedence(op1))
                    ) {
                        output.add(ops.pop())
                    }
                    ops.push(op1)
                } else {
                    // number (may include leading '-')
                    output.add(token)
                }
            }
            while (ops.isNotEmpty()) {
                output.add(ops.pop())
            }

            // Evaluate RPN
            val stack = ArrayDeque<BigDecimal>()
            for (t in output) {
                if (isOperator(t)) {
                    if (stack.size < 2) return null
                    val b = stack.pop()
                    val a = stack.pop()
                    val res = when (t) {
                        "+" -> a.add(b)
                        "-" -> a.subtract(b)
                        "*" -> a.multiply(b)
                        "/" -> {
                            if (b.compareTo(BigDecimal.ZERO) == 0) return null
                            a.divide(b, 20, RoundingMode.HALF_UP)
                        }
                        else -> return null
                    }
                    stack.push(res)
                } else {
                    // parse number
                    // handle lone "-" (invalid), but BigDecimal handles "-5" and "3.14"
                    stack.push(BigDecimal(t))
                }
            }
            if (stack.size != 1) return null
            val result = stack.pop().stripTrailingZeros()
            // format plain string; avoid "-0"
            val plain = result.toPlainString()
            if (plain == "-0") "0" else plain
        } catch (e: Exception) {
            null
        }
    }
}
