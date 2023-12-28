package com.example.braintrainhcmiu.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

import com.example.braintrainhcmiu.data.User

@Dao interface UserDAO {

  @Query("SELECT * FROM user")
  fun getAll(): List<User>

  @Query("SELECT * FROM user WHERE id IN (:userIds)")
  fun loadAllByIds(userIds: IntArray): List<User>

  @Insert()
  fun insertAll(vararg users: User)

  @Update
  fun update(user: User)

  @Delete
  fun delete(user: User)

}