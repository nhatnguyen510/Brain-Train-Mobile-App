package com.example.braintrainhcmiu.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update

import com.example.braintrainhcmiu.data.User
import kotlinx.coroutines.flow.Flow

@Dao interface UserDAO {

  @Query("SELECT * FROM user")
  fun getAll(): Flow<List<User>>

  @Query("SELECT * FROM user WHERE id = :id")
  fun getUser(id: Int): Flow<User>

  @Query("SELECT * FROM user WHERE id = :id")
  fun getUserAsync(id: Int): User

  // Update user's score
  @Query("UPDATE user SET compareScore = :compareScore WHERE id = :id")
  fun updateCompareScore(id: Int, compareScore: Int)

  @Query("UPDATE user SET findOperatorScore = :findOperatorScore WHERE id = :id")
  fun updateFindOperatorScore(id: Int, findOperatorScore: Int)

  @Query("UPDATE user SET conjunctionScore = :conjunctionScore WHERE id = :id")
  fun updateConjunctionScore(id: Int, conjunctionScore: Int)

  @Query("UPDATE user SET sortingCharScore = :sortingCharScore WHERE id = :id")
  fun updateSortingCharScore(id: Int, sortingCharScore: Int)

  @Query("SELECT * FROM user WHERE id IN (:userIds)")
  fun loadAllByIds(userIds: IntArray): Flow<List<User>>

  @Insert (onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(vararg users: User)

  @Update
  fun update(user: User)

  @Delete
  fun delete(user: User)

}