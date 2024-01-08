package com.example.braintrainhcmiu

import com.example.braintrainhcmiu.DAO.CompareMathGameDAO
import com.example.braintrainhcmiu.DAO.FindOperatorGameDAO
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.data.FindOperatorGame
import kotlinx.coroutines.flow.Flow

class FindOperatorGameRepository(private val findOperatorGameDAO: FindOperatorGameDAO) {
    val findOperatorGameQuestions: Flow<List<FindOperatorGame>> = findOperatorGameDAO.getAll()

}