package com.example.calculator

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.Date

class ExpressionViewModel(private val application: Application): AndroidViewModel(application) {
    private val repository: ExpressionRepository

    private val _expressions = MutableStateFlow(emptyList<Expression>())
    val expressions: StateFlow<List<Expression>> get() = _expressions.asStateFlow()
    var expression by mutableStateOf("")
    private var calculated: Boolean = false


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
        if (calculated){
            expression = ""
            calculated = false
        }
        when (action){
            is CalculatorAction.Symbol -> expression += action.value
            is CalculatorAction.Operation -> expression += action.operation.symbol
            is CalculatorAction.Delete -> expression = expression.dropLast(1)
            is CalculatorAction.Clear -> clear()
            is CalculatorAction.Calculate -> calculate(expression)
        }
    }

    private fun clear(){
        if (expression.isNotEmpty()) {
            expression = ""
        }
    }

    private fun calculate(string: String) =
        ExpressionBuilder(string).build().also { builder ->
            if (builder.validate().isValid) {
                builder.evaluate().also {
                    addExpression(Expression(0, expression, Date()))
                    expression = "=${if (it % 1 == 0.toDouble()) it.toInt() else it}"
                    calculated = true
                }
            } else {
                Toast.makeText(
                    application.applicationContext,
                    "Некорректное выражение",
                    Toast.LENGTH_LONG
                ).show()

            }
        }


}