package edu.unibo.tracker.Database


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Workout(
    @PrimaryKey var id : String,
    val activityType: String?, // Activity type (Running, Cycling, etc.)
    val workoutName: String?, // Workout name
    val intensity: String?, // Intensity level (Low, Medium, High)
    val category: String?, // Category (Cardio, Strength, Flexibility, etc.)
    val difficulty: String?, // Difficulty level (Beginner, Intermediate, Advanced)
    val equipment: String?, // Equipment needed (None, Weights, Resistance bands, etc.)
    val duration: String?, // Duration in minutes
    val calories: String?, // Estimated calories burned
    val imageURL : String?,
    val isFavorite: Boolean = false // Favorite status
)


/*var workouts = listOf(
    Workout(
        "1","Running", "Morning Jog","Medium","Cardio","Beginner","None","30","300","https://example.com/running.jpg"
    ),
    Workout(
        "2","Cycling", "Hill Climb","High","Cardio","Intermediate","Bicycle","45","450","https://example.com/cycling.jpg"
    ),
    Workout(
        "3","Strength", "Upper Body","High","Strength","Advanced","Weights","60","350","https://example.com/strength.jpg"
    ),
)*/
