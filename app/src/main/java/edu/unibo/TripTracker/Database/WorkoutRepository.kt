package edu.unibo.tracker.Database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WorkoutRepository @Inject constructor(private val workoutDAO: WorkoutDAO) {


    suspend fun addWorkout(newWorkout: Workout) {
        workoutDAO.insertWorkout(newWorkout)
    }
    fun getAllWorkouts(): Flow<List<Workout>> = workoutDAO.getAllWorkouts()

    suspend fun updateWorkout(workout: Workout) {
        workoutDAO.updateWorkout(workout)
    }

    suspend fun getFavoriteWorkouts(): List<Workout> {
        return workoutDAO.getFavoriteWorkouts()
    }

}

