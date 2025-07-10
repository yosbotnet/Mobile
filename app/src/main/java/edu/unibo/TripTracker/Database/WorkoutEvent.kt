package edu.unibo.tracker.Database



sealed class WorkoutEvent{
    data class AddWorkout(val workout: Workout) : WorkoutEvent()
    data class ToggleFavorite(val workout: Workout) : WorkoutEvent()
    object GetAllWorkout : WorkoutEvent()
    object GetWeeklyProgress : WorkoutEvent()
    object GetUpcomingWorkout : WorkoutEvent()
    object GetBestWorkout : WorkoutEvent()
}
