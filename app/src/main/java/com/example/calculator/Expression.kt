package com.example.calculator

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity
data class Expression (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val body: String,
    val date: Date
)