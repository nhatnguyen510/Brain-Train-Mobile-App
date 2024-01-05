package com.example.braintrainhcmiu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compareMathGame")
data class CompareMathGame(
  @PrimaryKey(autoGenerate = true)
  val id: Int? = null,

  @ColumnInfo(name = "level")
  val level: Int? = null,

  @ColumnInfo(name = "score")
  val score: Int? = null,

  @ColumnInfo(name = "expression1")
  val Expression1: String? = null,

  @ColumnInfo(name = "expression2")
  val Expression2: String? = null,

  @ColumnInfo(name = "expressionResult1")
  val ExpressionResult1: String? = null,

  @ColumnInfo(name = "expressionResult2")
  val ExpressionResult2: String? = null,

  @ColumnInfo(name = "completeStatus")
  val completeStatus: Int? = null,
)
