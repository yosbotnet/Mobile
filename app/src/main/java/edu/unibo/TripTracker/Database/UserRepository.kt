package edu.unibo.tracker.Database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDAO: UserDAO) {

    suspend fun insert(user: User) {
        userDAO.insert(user)
    }

    fun getUserByEmail(email: String): Flow<User?> = userDAO.getUserByEmail(email)

    suspend fun updateUser(user: User) {
        userDAO.updateUser(user)
    }
}