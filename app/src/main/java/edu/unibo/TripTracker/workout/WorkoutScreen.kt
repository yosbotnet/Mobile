package edu.unibo.tracker.workout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.unibo.tracker.CardPosWorkout
import edu.unibo.tracker.CardPosWorkout
import edu.unibo.tracker.commonItem.TopBarSec
import edu.unibo.tracker.Database.*
import edu.unibo.tracker.workout.Card.CardAddWorkout


@Composable
fun WorkoutScreen(
    navController: NavController, workoutViewModel: WorkoutViewModel
) {
    val workouts = workoutViewModel.allWorkout
    var searchText by remember { mutableStateOf("") }
    var showFavoritesOnly by remember { mutableStateOf(false) }
    
    val filteredWorkouts = workouts.filter { workout ->
        val matchesSearch = workout.activityType?.contains(searchText, ignoreCase = true) == true ||
                workout.workoutName?.contains(searchText, ignoreCase = true) == true ||
                workout.category?.contains(searchText, ignoreCase = true) == true
        
        val matchesFavoriteFilter = if (showFavoritesOnly) workout.isFavorite else true
        
        matchesSearch && matchesFavoriteFilter
    }
    
    Scaffold(topBar = { TopBarSec("Workouts", navController)
    }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search workouts") },
                    placeholder = { Text("Search by name, activity, or category") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    singleLine = true
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Show favorites only:")
                    Switch(
                        checked = showFavoritesOnly,
                        onCheckedChange = { showFavoritesOnly = it }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            items(filteredWorkouts) {
                CardPosWorkout(workout = it, workoutViewModel = workoutViewModel)
            }
            item {
                CardAddWorkout(workoutViewModel,navController)
            }
            item { Spacer(modifier = Modifier.height(6.dp)) }
        }
    }
}


