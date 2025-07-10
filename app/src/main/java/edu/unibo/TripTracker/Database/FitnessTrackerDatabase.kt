package edu.unibo.tracker.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Workout::class, Track::class, User::class], version = 3, exportSchema = false)
abstract class FitnessTrackerDatabase :RoomDatabase() {

    abstract fun workoutDao(): WorkoutDAO

    abstract fun trackDao(): TrackDAO

    abstract fun userDao(): UserDAO

    companion object{
        const val DATABASE_NAME = "fitness_tracker_db"
    }
}