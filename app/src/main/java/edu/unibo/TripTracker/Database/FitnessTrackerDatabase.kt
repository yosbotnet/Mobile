package edu.unibo.tracker.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Workout::class, Track::class, User::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FitnessTrackerDatabase :RoomDatabase() {

    abstract fun workoutDao(): WorkoutDAO

    abstract fun trackDao(): TrackDAO

    abstract fun userDao(): UserDAO

    companion object{
        const val DATABASE_NAME = "fitness_tracker_db"
    }
}