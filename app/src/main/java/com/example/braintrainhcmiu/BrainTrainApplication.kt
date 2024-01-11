package com.example.braintrainhcmiu

import android.app.Application
import com.example.braintrainhcmiu.database.AppDatabase

class BrainTrainApplication: Application() {
  private val database by lazy { AppDatabase.getDatabase(this) }

  val compareMathGameRepository by lazy { CompareMathGameRepository(database!!.compareMathGameDao()) }

  val findOperatorGameRepository by lazy { FindOperatorGameRepository(database!!.findOperatorGameDao() )}

  val userRepository by lazy { UserRepository(database!!.userDao()) }
}