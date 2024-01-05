package com.example.braintrainhcmiu.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.braintrainhcmiu.CompareMathGameRepository
import com.example.braintrainhcmiu.data.CompareMathGame

class CompareMathGameViewModel(private val compareMathGameRepository: CompareMathGameRepository): ViewModel() {
  var compareMathQuestions: LiveData<List<CompareMathGame>> = compareMathGameRepository.compareMathGameQuestions.asLiveData()

}

class CompareMathGameViewModelFactory(private val compareMathGameRepository: CompareMathGameRepository): ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CompareMathGameViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return CompareMathGameViewModel(compareMathGameRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
