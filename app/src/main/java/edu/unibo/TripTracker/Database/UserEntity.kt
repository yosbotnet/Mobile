package edu.unibo.tracker.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val passwordHash: String,
    val sex: String? = null,
    val registrationDate: Date? = null,
    val address: String? = null
)