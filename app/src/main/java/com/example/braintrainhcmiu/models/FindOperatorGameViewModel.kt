package com.example.braintrainhcmiu.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.braintrainhcmiu.CompareMathGameRepository
import com.example.braintrainhcmiu.FindOperatorGameRepository
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.data.FindOperatorGame

class FindOperatorGameViewModel(private val findOperatorGameRepository: FindOperatorGameRepository): ViewModel() {
  var findOperatorGameQuestions: LiveData<List<FindOperatorGame>> = findOperatorGameRepository.findOperatorGameQuestions.asLiveData()
}

class FindOperatorGameViewModelFactory(private val findOperatorGameRepository: FindOperatorGameRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FindOperatorGameViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return FindOperatorGameViewModel(findOperatorGameRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
