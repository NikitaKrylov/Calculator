package com.example.calculator

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpressionDao {
    @Insert
    suspend fun add(expr: Expression)

    @Upsert
    suspend fun upsert(expr: Expression)

    @Delete
    suspend fun delete(expr: Expression)

    @Query("SELECT * FROM expression ORDER BY date DESC")
    fun getAll(): Flow<List<Expression>>
}