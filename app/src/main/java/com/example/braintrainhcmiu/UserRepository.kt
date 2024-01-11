package com.example.braintrainhcmiu

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.braintrainhcmiu.DAO.CompareMathGameDAO
import com.example.braintrainhcmiu.DAO.FindOperatorGameDAO
import com.example.braintrainhcmiu.DAO.UserDAO
import com.example.braintrainhcmiu.data.CompareMathGame
import com.example.braintrainhcmiu.data.FindOperatorGame
import com.example.braintrainhcmiu.data.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDAO: UserDAO) {
    val allUsers: Flow<List<User>> = userDAO.getAll()

    fun getUser(id: Int): Flow<User> {
        return userDAO.getUser(id)
    }

    fun getUserAsync(id: Int): User {
        return userDAO.getUserAsync(id)
    }

    fun updateCompareScore(id: Int, compareScore: Int) {
        userDAO.updateCompareScore(id, compareScore)
    }

    fun updateFindOperatorScore(id: Int, findOperatorScore: Int) {
        userDAO.updateFindOperatorScore(id, findOperatorScore)
    }

    fun updateConjunctionScore(id: Int, conjunctionScore: Int) {
        userDAO.updateConjunctionScore(id, conjunctionScore)
    }

    fun updateSortingCharScore(id: Int, sortingCharScore: Int) {
        userDAO.updateSortingCharScore(id, sortingCharScore)
    }

    fun loadAllByIds(userIds: IntArray): Flow<List<User>> {
        return userDAO.loadAllByIds(userIds)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User) {
        userDAO.insertAll(*users)
    }

    fun update(user: User) {
        userDAO.update(user)
    }

    fun delete(user: User) {
        userDAO.delete(user)
    }
}