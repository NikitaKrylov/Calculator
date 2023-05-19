package com.example.calculator

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.util.Date

class ExpressionViewModel(private val application: Application): AndroidViewModel(application) {
    private val repository: ExpressionRepository

    private val _expressions = MutableStateFlow(emptyList<Expression>())
    val expressions: StateFlow<List<Expression>> get() = _expressions.asStateFlow()
    var expression by mutableStateOf("")
    private var cleanForNext: Boolean = false
    private var memoryStore: String? = null


    init {
        val dao = AppDatabase.get(application).dao
        repository = ExpressionRepository(dao)

        viewModelScope.launch {
            repository.expressions.collect{
                _expressions.value = it
            }
        }
    }

    fun addExpression(expr: Expression){
        viewModelScope.launch(Dispatchers.IO){
            repository.add(expr)
        }
    }
    fun deleteExpression(expr: Expression){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(expr)
        }
    }

    fun onAction(action: CalculatorAction){
        if (cleanForNext){
            expression = ""
            cleanForNext = false
        }
        when (action){
            is CalculatorAction.Symbol -> expression += action.value
            is CalculatorAction.Operation -> expression += action.operation.symbol
            is CalculatorAction.Delete -> expression = expression.dropLast(1)
            is CalculatorAction.Clear -> clear()
            is CalculatorAction.Calculate -> calculate(expression)
            is CalculatorAction.MemoryRecall -> {}
            is CalculatorAction.MemoryStore -> {}
        }
    }

    private fun clear(){
        if (expression.isNotEmpty()) {
            expression = ""
        }
    }

    private fun memoryRecall(value: String?){
        expression += value ?: ""

    }

    private fun calculate(string: String) {
        try {
            val result = ExpressionBuilder(string).build().evaluate()
            addExpression(Expression(0, expression, Date()))
            expression = result.toString()
        } catch (e: Exception){
            expression = "Ошибка"
            cleanForNext = true
        }

//        ExpressionBuilder(string).build().also { builder ->
//            if (builder.validate().isValid) {
//                builder.evaluate().also {
//                    addExpression(Expression(0, expression, Date()))
//                    expression = "=${if (it % 1 == 0.toDouble()) it.toBits() else it}"
//                    calculated = true
//                }
//            } else {
//
//
//            }
        }


}