package com.example.braintrainhcmiu.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.braintrainhcmiu.data.CompareMathGame
import kotlinx.coroutines.flow.Flow

@Dao interface CompareMathGameDAO {

  @Query("SELECT * FROM compareMathGame")
  fun getAll(): Flow<List<CompareMathGame>>
}