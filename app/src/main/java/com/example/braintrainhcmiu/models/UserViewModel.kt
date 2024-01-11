package com.example.braintrainhcmiu.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.braintrainhcmiu.UserRepository
import com.example.braintrainhcmiu.data.User

class UserViewModel(private val userRepository: UserRepository): ViewModel() {
  val allUsers: LiveData<List<User>> = userRepository.allUsers.asLiveData()

  fun getUser(id: Int): LiveData<User> {
    return userRepository.getUser(id).asLiveData()
  }

  fun getUserAsync(id: Int): User {
    return userRepository.getUserAsync(id)
  }

  fun updateCompareScore(id: Int, compareScore: Int) {
    userRepository.updateCompareScore(id, compareScore)
  }

  fun updateFindOperatorScore(id: Int, findOperatorScore: Int) {
    userRepository.updateFindOperatorScore(id, findOperatorScore)
  }

  fun updateConjunctionScore(id: Int, conjunctionScore: Int) {
    userRepository.updateConjunctionScore(id, conjunctionScore)
  }

  fun updateSortingCharScore(id: Int, sortingCharScore: Int) {
    userRepository.updateSortingCharScore(id, sortingCharScore)
  }

  fun loadAllByIds(userIds: IntArray): LiveData<List<User>> {
    return userRepository.loadAllByIds(userIds).asLiveData()
  }

  fun insertAll(vararg users: User) {
    userRepository.insertAll(*users)
  }

  fun update(user: User) {
    userRepository.update(user)
  }

  fun delete(user: User) {
    userRepository.delete(user)
  }
}

class UserViewModelFactory(private val userRepository: UserRepository): androidx.lifecycle.ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return UserViewModel(userRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}