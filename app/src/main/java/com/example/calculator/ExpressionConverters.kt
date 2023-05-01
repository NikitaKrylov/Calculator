package com.example.calculator

import androidx.room.TypeConverter
import java.util.Date

class ExpressionConverters {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(time: Long): Date = Date(time)
}