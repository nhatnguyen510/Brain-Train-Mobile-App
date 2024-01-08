package com.example.braintrainhcmiu

import com.example.braintrainhcmiu.DAO.CompareMathGameDAO
import com.example.braintrainhcmiu.DAO.FindOperatorGameDAO
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.data.FindOperatorGame
import kotlinx.coroutines.flow.Flow

class CompareMathGameRepository(private val compareMathGameDao: CompareMathGameDAO) {
    val compareMathGameQuestions: Flow<List<CompareMathGame>> = compareMathGameDao.getAll()
}