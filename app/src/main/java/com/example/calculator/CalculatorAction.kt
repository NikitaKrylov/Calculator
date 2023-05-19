package com.example.calculator

sealed class CalculatorAction {
    data class Symbol(val value: String): CalculatorAction()
    object Clear: CalculatorAction()
    object Delete: CalculatorAction()
    object Calculate: CalculatorAction()
    object MemoryStore: CalculatorAction()
    object MemoryRecall: CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
}

