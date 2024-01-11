package com.example.braintrainhcmiu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String?,
    val email: String?,
    val compareScore: Int = 0,
    val findOperatorScore: Int = 0,
    val conjunctionScore: Int = 0,
    val sortingCharScore: Int = 0,
)
