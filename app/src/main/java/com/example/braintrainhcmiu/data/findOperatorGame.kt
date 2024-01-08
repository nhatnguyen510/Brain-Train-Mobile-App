package com.example.braintrainhcmiu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "findOperatorGame")
data class FindOperatorGame(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,

  @ColumnInfo(name = "target_sum")
  val target_sum: Int,

  @ColumnInfo(name = "options")
  val options: String,

  @ColumnInfo(name = "score")
  val score: Int
)
