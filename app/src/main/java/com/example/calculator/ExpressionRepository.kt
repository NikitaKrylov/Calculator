package com.example.calculator

class ExpressionRepository(private val dao: ExpressionDao) {
    val expressions = dao.getAll()
    suspend fun add(expr: Expression) = dao.add(expr)
    suspend fun delete(expr: Expression) = dao.delete(expr)

}