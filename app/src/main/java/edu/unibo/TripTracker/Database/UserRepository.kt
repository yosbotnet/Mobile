package edu.unibo.tracker.Database

import javax.inject.Inject

class UserRepository @Inject constructor(private val userDAO: UserDAO) {
    suspend fun insert(user: User) {
        userDAO.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDAO.getUserByEmail(email)
    }
}