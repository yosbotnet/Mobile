package edu.unibo.tracker.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkoutDAO{
    //----------------------------------    WORKOUT    ------------------------------------//
    @Insert(onConflict = OnConflictStrategy.IGNORE) //  INSERIMENTO WORKOUT
    suspend fun insertWorkout(workout: Workout)

    @Query("SELECT * FROM Workout")
    suspend fun getWorkouts() : List<Workout>

    @Query("SELECT workoutName FROM Workout LIMIT(1)")
    suspend fun getUpcomingWorkout() : String?

    @Query("SELECT workoutName FROM Workout LIMIT(1)")
    suspend fun getWeeklyProgressWorkout() : String?

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Query("SELECT * FROM Workout WHERE isFavorite = 1")
    suspend fun getFavoriteWorkouts() : List<Workout>
}