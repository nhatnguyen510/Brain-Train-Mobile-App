package com.example.braintrainhcmiu.DAO

import androidx.room.Dao
import androidx.room.Query
import com.example.braintrainhcmiu.data.FindOperatorGame
import kotlinx.coroutines.flow.Flow

@Dao interface FindOperatorGameDAO {

  @Query("SELECT * FROM findOperatorGame")
  fun getAll(): Flow<List<FindOperatorGame>>

}