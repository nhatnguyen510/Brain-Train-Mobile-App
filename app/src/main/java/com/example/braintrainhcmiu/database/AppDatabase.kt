package com.example.braintrainhcmiu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.braintrainhcmiu.DAO.UserDAO
import com.example.braintrainhcmiu.data.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}