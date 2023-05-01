package com.example.calculator

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpressionViewModel(private val application: Application): AndroidViewModel(application) {
    private val repository: ExpressionRepository

    private val _expressions = MutableStateFlow(emptyList<Expression>())
    val expressions: StateFlow<List<Expression>> get() = _expressions.asStateFlow()


    val currentHistoryExpression = MutableStateFlow(emptyList<Expression>())

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
}