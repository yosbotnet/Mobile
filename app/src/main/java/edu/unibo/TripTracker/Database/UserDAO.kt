package edu.unibo.tracker.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): Flow<User?>

    @Update
    suspend fun updateUser(user: User)
}