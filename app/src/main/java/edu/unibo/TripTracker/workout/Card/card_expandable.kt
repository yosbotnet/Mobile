package edu.unibo.tracker

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.unibo.tracker.Database.Workout
import edu.unibo.tracker.Database.WorkoutEvent
import edu.unibo.tracker.Database.WorkoutViewModel

//---------------------------------Creazione delle card----------------------------------------------//
@Composable
fun CardPosWorkout(workout: Workout, workoutViewModel: WorkoutViewModel? = null) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = 4.dp
    ) {
        ExpCardWorkout(workout, workoutViewModel)
    }
}
//----------------------------------Contenuto card espandibili---------------------------------------//
@Composable
private fun ExpCardWorkout(workout: Workout, workoutViewModel: WorkoutViewModel? = null) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))

        ) {
            workout.workoutName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold
                )
            }
            if (expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    AsyncImage(
                        modifier = Modifier.clip(RoundedCornerShape(5.dp)),
                        model = workout.imageURL,
                        contentDescription = "",
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    workout.activityType?.let { Text("Activity: $it") }
                    workout.intensity?.let { Text("Intensity: $it") }
                    workout.difficulty?.let { Text("Difficulty: $it") }
                    workout.equipment?.let { Text("Equipment: $it") }
                    workout.category?.let { Text("Category: $it") }
                    workout.calories?.let { Text("Calories: $it") }
                    workout.duration?.let { Text("Duration: $it min") }
                }
            }
        }
        Column {
            workoutViewModel?.let { viewModel ->
                IconButton(
                    onClick = { 
                        viewModel.onTriggerEvent(WorkoutEvent.ToggleFavorite(workout))
                    }
                ) {
                    Icon(
                        imageVector = if (workout.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (workout.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (workout.isFavorite) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
                    )
                }
            }
            OutlinedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more", color = MaterialTheme.colors.onSurface)
            }
        }
    }
}


