package com.example.braintrainhcmiu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.braintrainhcmiu.DAO.CompareMathGameDAO
import com.example.braintrainhcmiu.DAO.FindOperatorGameDAO
import com.example.braintrainhcmiu.DAO.UserDAO
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.data.FindOperatorGame
import com.example.braintrainhcmiu.data.User


@Database(entities = [User::class, CompareMathGame::class, FindOperatorGame::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    abstract fun compareMathGameDao(): CompareMathGameDAO

    abstract fun findOperatorGameDao(): FindOperatorGameDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "braintraindatabase"

        fun getDatabase(context: Context): AppDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().createFromAsset("databases/braintrainhcmiu.db").build()
                /*<<<<<<<<<< ADDED to FORCE an open of the database >>>>>>>>>>*/
                val sdb = instance!!.openHelper.writableDatabase
                INSTANCE = instance
                instance
            }
        }
    }



}