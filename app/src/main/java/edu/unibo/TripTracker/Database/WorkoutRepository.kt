package edu.unibo.tracker.Database

import javax.inject.Inject


class WorkoutRepository @Inject constructor(private val workoutDAO: WorkoutDAO) {


    suspend fun addWorkout(newWorkout: Workout) {
        workoutDAO.insertWorkout(newWorkout)
    }
    suspend fun getAllWorkouts(): List<Workout> {
        return workoutDAO.getWorkouts()
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDAO.updateWorkout(workout)
    }

    suspend fun getFavoriteWorkouts(): List<Workout> {
        return workoutDAO.getFavoriteWorkouts()
    }

}

