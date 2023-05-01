package com.example.calculator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Expression::class], version = 1)
@TypeConverters(ExpressionConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: ExpressionDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}