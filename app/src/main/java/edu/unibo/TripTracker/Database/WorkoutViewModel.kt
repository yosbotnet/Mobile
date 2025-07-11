package edu.unibo.tracker.Database

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class WorkoutViewModel
@Inject
constructor(
    private val workoutDAO: WorkoutDAO
) : ViewModel() {
    private val _allWorkouts = MutableStateFlow<List<Workout>>(emptyList())
    val allWorkouts = _allWorkouts.asStateFlow()

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
                        repository.getAllWorkouts().onEach { 
                            _allWorkouts.value = it
                        }.launchIn(viewModelScope)
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
    }
    private suspend fun getUpcomingWorkout() {
        upcomingWorkout = _allWorkouts.value.firstOrNull()?.workoutName
        upcomingDate = "Today"
    }
    private suspend fun getWeeklyProgress(){
        val totalWorkouts = _allWorkouts.value.size
        weeklyGoal = "7 workouts"
        weeklyProgress = "$totalWorkouts/7"
    }

    private suspend fun getBestWorkout(){
        val highestCalories = _allWorkouts.value.maxByOrNull { it.calories?.toIntOrNull() ?: 0 }
        bestWorkout = highestCalories?.calories
        bestWorkoutName = highestCalories?.workoutName
    }

    private suspend fun toggleFavorite(workout: Workout) {
        val updatedWorkout = workout.copy(isFavorite = !workout.isFavorite)
        repository.updateWorkout(updatedWorkout)
    }
}