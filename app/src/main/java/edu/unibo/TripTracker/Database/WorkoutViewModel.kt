package edu.unibo.tracker.Database

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel
@Inject
constructor(
    workoutDAO: WorkoutDAO
) : ViewModel() {
     val allWorkout = mutableStateListOf<Workout>()

    private val repository: WorkoutRepository = WorkoutRepository(workoutDAO)


    var bestWorkout: String? = null
    var bestWorkoutName: String? = null
    var upcomingWorkout: String? = null
    var upcomingDate: String? = null
    var weeklyGoal : String? = null
    var weeklyProgress : String? = null
    var workout: Workout? = null

        private set
    init {
        onTriggerEvent(WorkoutEvent.GetAllWorkout)
    }
    fun onTriggerEvent(event: WorkoutEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (event) {
                    is WorkoutEvent.AddWorkout -> {
                        addWorkout(event.workout)
                    }
                    is WorkoutEvent.GetAllWorkout -> {
                        allWorkout.clear()
                        allWorkout.addAll(repository.getAllWorkouts())
                    }
                    is WorkoutEvent.GetUpcomingWorkout -> {
                        getUpcomingWorkout()
                    }
                    is WorkoutEvent.GetWeeklyProgress -> {
                        getWeeklyProgress()
                    }
                    is WorkoutEvent.GetBestWorkout ->{
                        getBestWorkout()
                    }
                    is WorkoutEvent.ToggleFavorite -> {
                        toggleFavorite(event.workout)
                    }
                }
            } catch (e : Exception){
                Log.e("Errore",e.message.toString())
            }
        }
    }
    private suspend fun addWorkout(workout: Workout) {
        repository.addWorkout(workout)
        allWorkout.add(workout)
    }
    private suspend fun getUpcomingWorkout() {
        upcomingWorkout = repository.getAllWorkouts().firstOrNull()?.workoutName
        upcomingDate = "Today"
    }
    private suspend fun getWeeklyProgress(){
        val totalWorkouts = repository.getAllWorkouts().size
        weeklyGoal = "7 workouts"
        weeklyProgress = "$totalWorkouts/7"
    }

    private suspend fun getBestWorkout(){
        val highestCalories = repository.getAllWorkouts().maxByOrNull { it.calories?.toIntOrNull() ?: 0 }
        bestWorkout = highestCalories?.calories
        bestWorkoutName = highestCalories?.workoutName
    }

    private suspend fun toggleFavorite(workout: Workout) {
        val updatedWorkout = workout.copy(isFavorite = !workout.isFavorite)
        repository.updateWorkout(updatedWorkout)
        
        // Update local list
        val index = allWorkout.indexOfFirst { it.id == workout.id }
        if (index != -1) {
            allWorkout[index] = updatedWorkout
        }
    }
}